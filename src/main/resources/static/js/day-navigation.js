(function() {
  const match = window.location.pathname.match(/day(\d+)\.html$/);
  if (!match) return;
  const day = parseInt(match[1], 10);
  const month = Math.floor((day - 1) / 18) + 1;
  const week = Math.floor((day - 1) / 3) + 1;
  const prevDay = day > 1 ? `day${day - 1}.html` : null;
  const nextDay = day < 54 ? `day${day + 1}.html` : null;
  const lectureLink = `../lecture/day${day}_lecture.html`;

  // Remove existing navigation to avoid duplication
  document.querySelectorAll('body > nav').forEach(el => el.remove());
  document.querySelectorAll('nav[aria-label="breadcrumb"]').forEach(nav => {
    let container = nav;
    // Remove ancestor containers that only exist for breadcrumb
    while (
      container.parentElement &&
      container.parentElement !== document.body &&
      container.parentElement.children.length === 1
    ) {
      container = container.parentElement;
    }
    container.remove();
  });

  const head = document.head;
  const tailwind = document.createElement('link');
  tailwind.rel = 'stylesheet';
  tailwind.href = 'https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css';
  head.appendChild(tailwind);
  const fa = document.createElement('link');
  fa.rel = 'stylesheet';
  fa.href = 'https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.4.0/css/all.min.css';
  head.appendChild(fa);

  const style = document.createElement('style');
  style.textContent = `
    .gradient-bg { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
    .sticky-nav { position: sticky; top: 0; z-index: 100; backdrop-filter: blur(10px); }
    .month-badge { background: linear-gradient(45deg, #ffd700, #ffed4a); }
  `;
  head.appendChild(style);

  const prevLink = prevDay
    ? `<a href="${prevDay}" class="text-blue-600 hover:text-blue-800 font-medium"><i class=\"fas fa-chevron-left mr-1\"></i>前日</a>`
    : `<span class="opacity-50"><i class=\"fas fa-chevron-left mr-1\"></i>前日</span>`;
  const nextLink = nextDay
    ? `<a href="${nextDay}" class="text-blue-600 hover:text-blue-800 font-medium">翌日 <i class=\"fas fa-chevron-right ml-1\"></i></a>`
    : `<span class="opacity-50">翌日 <i class=\"fas fa-chevron-right ml-1\"></i></span>`;

  const navHtml = `
<header class="gradient-bg text-white sticky-nav">
  <div class="container mx-auto px-4 py-4">
    <div class="flex items-center justify-between">
      <div class="flex items-center space-x-4">
        <h1 class="text-2xl font-bold"><i class="fas fa-graduation-cap mr-2"></i>ITエンジニア育成カリキュラム</h1>
      </div>
      <nav class="flex items-center space-x-6">
        <a href="../month/month${month}.html" class="hover:text-yellow-300 transition-colors"><i class="fas fa-home mr-1"></i>トップ</a>
        <a href="../month/month${month}.html" class="hover:text-yellow-300 transition-colors"><i class="fas fa-layer-group mr-1"></i>${month}ヶ月目</a>
        <a href="../week/week${week}.html" class="hover:text-yellow-300 transition-colors"><i class="fas fa-calendar-week mr-1"></i>${week}週目</a>
      </nav>
    </div>
  </div>
</header>
<div class="bg-white border-b">
  <div class="container mx-auto px-4 py-3">
    <nav class="flex items-center space-x-2 text-sm text-gray-600">
      <a href="../month/month${month}.html" class="hover:text-blue-600"><i class="fas fa-home"></i> ホーム</a>
      <i class="fas fa-chevron-right text-gray-400"></i>
      <a href="../month/month${month}.html" class="hover:text-blue-600">${month}ヶ月目</a>
      <i class="fas fa-chevron-right text-gray-400"></i>
      <a href="../week/week${week}.html" class="hover:text-blue-600">${week}週目</a>
      <i class="fas fa-chevron-right text-gray-400"></i>
      <span class="text-blue-600 font-semibold">${day}日目</span>
    </nav>
  </div>
</div>
<div class="bg-blue-50 border-b">
  <div class="container mx-auto px-4 py-2">
    <div class="flex justify-between items-center">
      <div class="flex items-center space-x-4 text-sm">
        ${prevLink}
        <span class="text-gray-400">|</span>
      </div>
      <div class="flex items-center space-x-4 text-sm">
        <a href="${lectureLink}" class="inline-flex items-center px-3 py-1 border border-blue-300 rounded-md text-sm text-blue-600 hover:bg-green-300 transition-colors bg-white"><i class="fas fa-book mr-1"></i>講義ページ</a>
      </div>
      <div class="flex items-center space-x-4 text-sm">
        <span class="text-gray-400">|</span>
        ${nextLink}
      </div>
    </div>
  </div>
</div>`;

  const container = document.createElement('div');
  container.innerHTML = navHtml;
  document.body.insertBefore(container, document.body.firstChild);
})();
