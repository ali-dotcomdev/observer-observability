// ─── dashboard.js ────────────────────────────────────────────────────────────
// Lucide ikonlarını başlat
lucide.createIcons();

// ── 1. DOM CACHE ─────────────────────────────────────────────────────────────
// Her SSE mesajında getElementById çağırmak yerine referanslar bir kez alınır.
const DOM = {
    statusBadge:    document.getElementById('status-badge'),
    // CPU
    cpuLoad:        document.getElementById('cpu-load'),
    cpuBar:         document.getElementById('cpu-bar'),
    cpuCores:       document.getElementById('cpu-cores'),
    // RAM
    ramPercent:     document.getElementById('ram-percent'),
    ramBar:         document.getElementById('ram-bar'),
    usedRam:        document.getElementById('used-ram'),
    freeRam:        document.getElementById('free-ram'),
    totalRam:       document.getElementById('total-ram'),
    // Disk
    diskPercent:    document.getElementById('disk-percent'),
    diskBar:        document.getElementById('disk-bar'),
    usedDisk:       document.getElementById('used-disk'),
    freeDisk:       document.getElementById('free-disk'),
    totalDisk:      document.getElementById('total-disk'),
    diskTotalText:  document.getElementById('disk-total-text'),
    // Log
    logTerminal:    document.getElementById('log-terminal'),
};

// ── 2. PROGRESS BAR HELPER ───────────────────────────────────────────────────
// Üç farklı yerde tekrarlanan renk mantığı tek fonksiyona çekildi.
const BAR_COLORS = {
    cpu:  { n: 'from-blue-500 to-indigo-500',   w: 'from-amber-500 to-orange-500', d: 'from-red-500 to-rose-500' },
    ram:  { n: 'from-indigo-500 to-violet-500', w: 'from-amber-500 to-orange-500', d: 'from-red-500 to-rose-500' },
    disk: { n: 'from-emerald-500 to-teal-500',  w: 'from-amber-500 to-orange-500', d: 'from-red-500 to-rose-500' },
};

const BASE_BAR = 'bg-gradient-to-r h-full transition-all duration-500 rounded-full';

function updateBar(el, pct, colors, warnAt = 65, dangerAt = 85) {
    el.style.width = pct + '%';
    const c = pct > dangerAt ? colors.d : pct > warnAt ? colors.w : colors.n;
    el.className = `${BASE_BAR} ${c}`;
}

// ── 3. LOG SEVİYE STİLLERİ ───────────────────────────────────────────────────
// switch bloğu yerine O(1) nesne erişimi.
const BASE_BADGE = 'px-1.5 py-0.2 rounded text-[9px] font-bold tracking-wide shrink-0 border';
const LOG_STYLES = {
    ERROR: `${BASE_BADGE} bg-red-500/15    text-red-400    border-red-500/20`,
    FATAL: `${BASE_BADGE} bg-red-500/15    text-red-400    border-red-500/20`,
    WARN:  `${BASE_BADGE} bg-amber-500/15  text-amber-400  border-amber-500/20`,
    DEBUG: `${BASE_BADGE} bg-blue-500/15   text-blue-400   border-blue-500/20`,
    INFO:  `${BASE_BADGE} bg-emerald-500/15 text-emerald-400 border-emerald-500/20`,
};

// ── 4. CHART KURULUMU ────────────────────────────────────────────────────────
let latestCpu = 0, latestRam = 0;

const perfCtx  = document.getElementById('performanceChart').getContext('2d');
const diskCtx  = document.getElementById('diskDoughnutChart').getContext('2d');

// Gradient yardımcısı
function makeGradient(ctx, r, g, b) {
    const grad = ctx.createLinearGradient(0, 0, 0, 300);
    grad.addColorStop(0, `rgba(${r},${g},${b},0.2)`);
    grad.addColorStop(1, `rgba(${r},${g},${b},0)`);
    return grad;
}

// Dataset ortak seçenekleri
const DS_BASE = {
    borderWidth: 2, tension: 0.4, fill: true,
    pointBorderColor: '#0b0f19', pointBorderWidth: 1.5, pointRadius: 2,
};

const performanceChart = new Chart(perfCtx, {
    type: 'line',
    data: {
        labels: [],
        datasets: [
            { ...DS_BASE, label: 'CPU Yükü (%)',       data: [], borderColor: '#3b82f6', backgroundColor: makeGradient(perfCtx, 59,  130, 246), pointBackgroundColor: '#3b82f6' },
            { ...DS_BASE, label: 'RAM Kullanımı (%)',  data: [], borderColor: '#6366f1', backgroundColor: makeGradient(perfCtx, 99,  102, 241), pointBackgroundColor: '#6366f1' },
        ],
    },
    options: {
        responsive: true, maintainAspectRatio: false,
        animation: { duration: 250 },
        scales: {
            y: { min: 0, max: 100, grid: { color: 'rgba(255,255,255,0.03)' }, ticks: { color: '#94a3b8', font: { family: 'Inter', size: 10 } } },
            x: {                   grid: { color: 'rgba(255,255,255,0.03)' }, ticks: { color: '#94a3b8', font: { family: 'Inter', size: 10 } } },
        },
        plugins: {
            legend:  { display: true,  labels: { color: '#94a3b8', font: { family: 'Inter', size: 11 }, usePointStyle: true, pointStyle: 'circle' } },
            tooltip: { backgroundColor: '#1e293b', titleColor: '#f8fafc', bodyColor: '#f8fafc', borderColor: 'rgba(255,255,255,0.1)', borderWidth: 1 },
        },
    },
});

const diskChart = new Chart(diskCtx, {
    type: 'doughnut',
    data: {
        labels: ['Kullanılan Alan (GB)', 'Boş Alan (GB)'],
        datasets: [{ data: [0, 100], backgroundColor: ['#10b981', '#1e293b'], borderWidth: 2, borderColor: '#0b0f19', hoverOffset: 4 }],
    },
    options: {
        responsive: true, maintainAspectRatio: false, cutout: '75%',
        plugins: {
            legend: { position: 'bottom', labels: { color: '#94a3b8', font: { family: 'Inter', size: 11 }, usePointStyle: true, pointStyle: 'circle' } },
        },
    },
});

// ── 5. SSE BAĞLANTISI ────────────────────────────────────────────────────────
const eventSource = new EventSource('/stream/metrics');

eventSource.onopen = () => {
    DOM.statusBadge.innerHTML = `<span class="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span><span>SSE BAĞLANTISI AKTİF</span>`;
    DOM.statusBadge.className = 'flex items-center gap-2 px-3.5 py-1.5 bg-emerald-500/10 text-emerald-400 rounded-full border border-emerald-500/20 text-xs font-semibold tracking-wide';
};

eventSource.onerror = () => {
    DOM.statusBadge.innerHTML = `<span class="w-2 h-2 rounded-full bg-rose-500 animate-pulse"></span><span>BAĞLANTI KESİLDİ</span>`;
    DOM.statusBadge.className = 'flex items-center gap-2 px-3.5 py-1.5 bg-rose-500/10 text-rose-400 rounded-full border border-rose-500/20 text-xs font-semibold tracking-wide';
};

eventSource.onmessage = ({ data: raw }) => {
    let d;
    try { d = JSON.parse(raw); } catch { return; }

    // Nested record'ları destructure et — eksikse boş obje düşer, null hatası olmaz
    const cpu  = d.cpuRecord  ?? {};
    const ram  = d.ramRecord  ?? {};
    const disk = d.diskRecord ?? {};
    const log  = d.logRecord  ?? {};

    // CPU
    if (cpu.cpuLoad != null) {
        latestCpu = parseFloat(cpu.cpuLoad);
        DOM.cpuLoad.textContent = latestCpu.toFixed(1) + '%';
        updateBar(DOM.cpuBar, latestCpu, BAR_COLORS.cpu);
    }
    if (cpu.availableProcessors != null) {
        DOM.cpuCores.textContent = cpu.availableProcessors + ' Çekirdek';
    }

    // RAM
    if (ram.usedMemoryMb != null && ram.totalMemoryMb != null) {
        const used = +ram.usedMemoryMb, total = +ram.totalMemoryMb;
        const free = ram.freeMemoryMb != null ? +ram.freeMemoryMb : total - used;
        const pct  = total > 0 ? (used / total) * 100 : 0;
        latestRam = pct;
        DOM.ramPercent.textContent = pct.toFixed(1) + '%';
        DOM.usedRam.textContent    = used  + ' MB';
        DOM.freeRam.textContent    = free  + ' MB';
        DOM.totalRam.textContent   = total + ' MB';
        updateBar(DOM.ramBar, pct, BAR_COLORS.ram);
    }

    // Disk
    if (disk.usedSpaceGb != null && disk.totalSpaceGb != null) {
        const used = +disk.usedSpaceGb, total = +disk.totalSpaceGb;
        const free = disk.freeSpaceGb != null ? +disk.freeSpaceGb : total - used;
        const pct  = total > 0 ? (used / total) * 100 : 0;
        DOM.diskPercent.textContent  = pct.toFixed(1) + '%';
        DOM.usedDisk.textContent     = used  + ' GB';
        DOM.freeDisk.textContent     = free  + ' GB';
        DOM.totalDisk.textContent    = total + ' GB';
        DOM.diskTotalText.textContent = total + ' GB';
        updateBar(DOM.diskBar, pct, BAR_COLORS.disk, 70, 90);
        diskChart.data.datasets[0].data            = [used, free];
        diskChart.data.datasets[0].backgroundColor = [pct > 85 ? '#f43f5e' : '#10b981', '#1e293b'];
        diskChart.update();
    }

    // Zaman serisi grafiği
    if (cpu.cpuLoad != null || ram.usedMemoryMb != null) {
        const t = new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' });
        performanceChart.data.labels.push(t);
        performanceChart.data.datasets[0].data.push(latestCpu);
        performanceChart.data.datasets[1].data.push(latestRam);
        if (performanceChart.data.labels.length > 20) {
            performanceChart.data.labels.shift();
            performanceChart.data.datasets[0].data.shift();
            performanceChart.data.datasets[1].data.shift();
        }
        performanceChart.update();
    }
};

// ── 5.2 LOG SSE BAĞLANTISI ───────────────────────────────────────────────────
const logEventSource = new EventSource('/stream/logs');

logEventSource.onmessage = ({ data: raw }) => {
    let log;
    try {
        log = JSON.parse(raw);
    } catch {
        // Gelen veri JSON formatında değilse ham metin olarak yazdırmayı deneyebiliriz
        appendLog('INFO', raw, new Date().toISOString());
        return;
    }

    if (log && log.logLevel != null && log.message != null) {
        appendLog(log.logLevel, log.message, log.timestamp);
    }
};

logEventSource.onerror = () => {
    console.warn("Log akışı bağlantısı kesildi, otomatik olarak yeniden bağlanmaya çalışılacak...");
};

// ── 6. LOG TERMİNALİ ─────────────────────────────────────────────────────────
function appendLog(level, message, timestamp) {
    // Placeholder'ı ilk gerçek log gelince temizle
    if (DOM.logTerminal.children.length === 1 && DOM.logTerminal.children[0].classList.contains('italic')) {
        DOM.logTerminal.innerHTML = '';
    }

    const lvl  = (level || 'INFO').toUpperCase();
    let   time = '';
    try { time = new Date(timestamp).toLocaleTimeString(); } catch { time = new Date().toLocaleTimeString(); }

    const row   = document.createElement('div');
    row.className = 'flex items-start gap-2 py-0.5 border-b border-slate-900/40 text-[11px] leading-relaxed';

    const tSpan = Object.assign(document.createElement('span'), {
        className:   'text-slate-500 shrink-0 select-none',
        textContent: `[${time}]`,
    });
    const lSpan = Object.assign(document.createElement('span'), {
        className:   LOG_STYLES[lvl] ?? LOG_STYLES.INFO,
        textContent: lvl,
    });
    const mSpan = Object.assign(document.createElement('span'), {
        className:   'text-slate-300 break-all',
        textContent: message,
    });

    row.append(tSpan, lSpan, mSpan);
    DOM.logTerminal.appendChild(row);
    DOM.logTerminal.scrollTop = DOM.logTerminal.scrollHeight;

    // Maksimum 100 satır tut
    if (DOM.logTerminal.children.length > 100) {
        DOM.logTerminal.removeChild(DOM.logTerminal.firstChild);
    }
}

function clearLogs() {
    DOM.logTerminal.innerHTML = '<div class="text-slate-500 italic">Terminal temizlendi. Yeni loglar bekleniyor...</div>';
}