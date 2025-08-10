(function() {
  const match = window.location.pathname.match(/day(\d+)\.html$/);
  if (!match) return;
  const day = parseInt(match[1], 10);
  const month = Math.floor((day - 1) / 18) + 1;
  const week = Math.floor((day - 1) / 3) + 1;
  const prevDay = day > 1 ? `day${day - 1}.html` : null;
  const nextDay = day < 54 ? `day${day + 1}.html` : null;
  const lectureLink = `../lecture/lecture${day}.html`;

  // Remove existing navigation to avoid duplication
  document.querySelectorAll('body > nav').forEach(el => el.remove());
  document.querySelectorAll('nav[aria-label="breadcrumb"]').forEach(nav => {
    let container = nav;
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
  const fa = document.createElement('link');
  fa.rel = 'stylesheet';
  fa.href = 'https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.4.0/css/all.min.css';
  head.appendChild(fa);

  const style = document.createElement('style');
  style.textContent = `.nav-gap { gap: .5rem; }`;
  head.appendChild(style);

  const prevLink = prevDay
    ? `<a href="${prevDay}" class="text-decoration-none"><i class=\"fas fa-chevron-left me-1\"></i>前日</a>`
    : `<span class="text-muted"><i class=\"fas fa-chevron-left me-1\"></i>前日</span>`;
  const nextLink = nextDay
    ? `<a href="${nextDay}" class="text-decoration-none">翌日 <i class=\"fas fa-chevron-right ms-1\"></i></a>`
    : `<span class="text-muted">翌日 <i class=\"fas fa-chevron-right ms-1\"></i></span>`;

  const navHtml = `
<nav class="navbar navbar-expand-lg navbar-dark bg-primary sticky-top">
  <div class="container-fluid">
    <a class="navbar-brand" href="../month/month${month}.html"><i class="fas fa-graduation-cap me-2"></i>ITエンジニア育成カリキュラム</a>
    <div class="navbar-nav nav-gap">
      <a class="nav-link" href="../month/month${month}.html"><i class="fas fa-layer-group me-1"></i>${month}ヶ月目</a>
      <a class="nav-link" href="../week/week${week}.html"><i class="fas fa-calendar-week me-1"></i>${week}週目</a>
    </div>
  </div>
</nav>
<nav aria-label="breadcrumb" class="bg-light border-bottom">
  <ol class="breadcrumb container my-2">
    <li class="breadcrumb-item"><a href="../month/month${month}.html"><i class="fas fa-home"></i> ホーム</a></li>
    <li class="breadcrumb-item"><a href="../month/month${month}.html">${month}ヶ月目</a></li>
    <li class="breadcrumb-item"><a href="../week/week${week}.html">${week}週目</a></li>
    <li class="breadcrumb-item active" aria-current="page">${day}日目</li>
  </ol>
</nav>
<div class="bg-white border-bottom">
  <div class="container py-2 d-flex justify-content-between align-items-center">
    <div>${prevLink}</div>
    <div><a href="${lectureLink}" target="_blank" class="btn btn-warning btn-sm">講義スライドを開く</a></div>
    <div>${nextLink}</div>
  </div>
</div>
`;
  document.body.insertAdjacentHTML('afterbegin', navHtml);
})();
