/**
 * ITエンジニア育成カリキュラム - メインJavaScriptファイル
 * 作成日: 2025-07-28
 */

// ドキュメントの読み込み完了時に実行
document.addEventListener('DOMContentLoaded', function() {
  // タブ切り替え機能
  initTabs();
  
  // ナビゲーションのアクティブ状態設定
  setActiveNavItem();
  
  // 検索機能
  initSearch();
  
  // プログレストラッカー
  initProgressTracker();
  
  // 印刷ボタン
  initPrintButton();

  // ダークモード切り替え
  initDarkModeToggle();
});

/**
 * タブ切り替え機能の初期化
 */
function initTabs() {
  const tabs = document.querySelectorAll('.tab');
  if (tabs.length === 0) return;
  
  tabs.forEach(tab => {
    tab.addEventListener('click', function() {
      // 同じグループの全てのタブとコンテンツを非アクティブに
      const tabGroup = this.closest('.tabs-container');
      const allTabs = tabGroup.querySelectorAll('.tab');
      const allContents = tabGroup.querySelectorAll('.tab-content');
      
      allTabs.forEach(t => t.classList.remove('active'));
      allContents.forEach(c => c.classList.remove('active'));
      
      // クリックされたタブとそれに対応するコンテンツをアクティブに
      this.classList.add('active');
      const targetId = this.getAttribute('data-tab');
      const targetContent = document.getElementById(targetId);
      if (targetContent) {
        targetContent.classList.add('active');
      }
      
      // ローカルストレージにアクティブなタブを保存
      const pageId = window.location.pathname.split('/').pop().replace('.html', '');
      localStorage.setItem(`activeTab_${pageId}`, targetId);
    });
  });
  
  // ページ読み込み時に保存されたタブを復元
  const pageId = window.location.pathname.split('/').pop().replace('.html', '');
  const savedTabId = localStorage.getItem(`activeTab_${pageId}`);
  if (savedTabId) {
    const savedTab = document.querySelector(`.tab[data-tab="${savedTabId}"]`);
    if (savedTab) {
      savedTab.click();
    } else {
      // デフォルトで最初のタブをアクティブに
      const firstTab = document.querySelector('.tab');
      if (firstTab) firstTab.click();
    }
  } else {
    // デフォルトで最初のタブをアクティブに
    const firstTab = document.querySelector('.tab');
    if (firstTab) firstTab.click();
  }
}

/**
 * 現在のページに対応するナビゲーションアイテムをアクティブに設定
 */
function setActiveNavItem() {
  const currentPath = window.location.pathname;
  const navLinks = document.querySelectorAll('.main-nav a');
  
  navLinks.forEach(link => {
    const linkPath = link.getAttribute('href');
    if (currentPath.includes(linkPath) && linkPath !== '/') {
      link.classList.add('active');
    } else if (currentPath === '/' && linkPath === '/') {
      link.classList.add('active');
    }
  });
}

/**
 * 検索機能の初期化
 */
function initSearch() {
  const searchInput = document.getElementById('search-input');
  if (!searchInput) return;
  
  searchInput.addEventListener('input', function() {
    const searchTerm = this.value.toLowerCase();
    const searchResults = document.getElementById('search-results');
    
    // 検索結果を表示する要素がなければ作成
    if (!searchResults) {
      const resultsContainer = document.createElement('div');
      resultsContainer.id = 'search-results';
      resultsContainer.classList.add('search-results');
      this.parentNode.appendChild(resultsContainer);
    }
    
    if (searchTerm.length < 2) {
      const resultsElement = document.getElementById('search-results');
      if (resultsElement) {
        while (resultsElement.firstChild) {
          resultsElement.removeChild(resultsElement.firstChild);
        }
      }
      return;
    }
    
    // 検索対象要素
    const searchables = document.querySelectorAll('.searchable');
    let results = [];
    
    searchables.forEach(item => {
      const text = item.textContent.toLowerCase();
      const title = item.getAttribute('data-title') || '';
      const url = item.getAttribute('data-url') || '#';
      
      if (text.includes(searchTerm) || title.toLowerCase().includes(searchTerm)) {
        results.push({
          title: title || text.substring(0, 50) + '...',
          url: url,
          excerpt: text.substring(0, 100) + '...'
        });
      }
    });
    
    // 結果の表示
    const resultsElement = document.getElementById('search-results');
    while (resultsElement.firstChild) {
      resultsElement.removeChild(resultsElement.firstChild);
    }

    if (results.length > 0) {
      const ul = document.createElement('ul');
      results.forEach(result => {
        const li = document.createElement('li');
        const link = document.createElement('a');
        link.href = result.url;
        link.textContent = result.title;

        const p = document.createElement('p');
        p.textContent = result.excerpt;

        li.appendChild(link);
        li.appendChild(p);
        ul.appendChild(li);
      });
      resultsElement.appendChild(ul);
    } else {
      const p = document.createElement('p');
      p.textContent = '検索結果が見つかりませんでした。';
      resultsElement.appendChild(p);
    }
  });
  
  // 検索結果外をクリックしたら結果を非表示
  document.addEventListener('click', function(event) {
    const searchResults = document.getElementById('search-results');
    const searchInput = document.getElementById('search-input');
    
    if (searchResults && !searchResults.contains(event.target) && event.target !== searchInput) {
      while (searchResults.firstChild) {
        searchResults.removeChild(searchResults.firstChild);
      }
    }
  });
}

/**
 * プログレストラッカー機能の初期化
 */
function initProgressTracker() {
  const progressBars = document.querySelectorAll('.progress-bar');
  if (progressBars.length === 0) return;
  
  progressBars.forEach(bar => {
    const progress = bar.getAttribute('data-progress') || '0';
    bar.style.width = progress + '%';
    bar.textContent = progress + '%';
  });
  
  // チェックリストの進捗を更新
  const checklistItems = document.querySelectorAll('.checklist-item input[type="checkbox"]');
  if (checklistItems.length > 0) {
    checklistItems.forEach(item => {
      // ローカルストレージから状態を復元
      const itemId = item.getAttribute('id');
      const isChecked = localStorage.getItem(`checklist_${itemId}`) === 'true';
      item.checked = isChecked;
      
      item.addEventListener('change', function() {
        // 状態をローカルストレージに保存
        localStorage.setItem(`checklist_${itemId}`, this.checked);
        
        // 進捗バーを更新
        updateChecklistProgress();
      });
    });
    
    // 初期ロード時に進捗バーを更新
    updateChecklistProgress();
  }
}

/**
 * チェックリストの進捗状況を更新
 */
function updateChecklistProgress() {
  const checklists = document.querySelectorAll('.checklist');
  
  checklists.forEach(list => {
    const items = list.querySelectorAll('input[type="checkbox"]');
    const totalItems = items.length;
    if (totalItems === 0) return;
    
    const checkedItems = list.querySelectorAll('input[type="checkbox"]:checked').length;
    const progressPercent = Math.round((checkedItems / totalItems) * 100);
    
    const progressBar = list.querySelector('.progress-bar');
    if (progressBar) {
      progressBar.style.width = progressPercent + '%';
      progressBar.textContent = progressPercent + '%';
    }
    
    // データ属性にも保存（他の機能から参照可能）
    list.setAttribute('data-progress', progressPercent);
  });
}

/**
 * 印刷ボタン機能の初期化
 */
function initPrintButton() {
  const printButtons = document.querySelectorAll('.print-button');
  if (printButtons.length === 0) return;
  
  printButtons.forEach(button => {
    button.addEventListener('click', function() {
      window.print();
    });
  });
}

/**
 * ダークモード切り替え機能の初期化
 */
function initDarkModeToggle() {
  const darkModeToggle = document.getElementById('dark-mode-toggle');
  if (!darkModeToggle) return;
  
  // ローカルストレージからダークモード設定を取得
  const isDarkMode = localStorage.getItem('darkMode') === 'true';
  
  // 初期状態を設定
  if (isDarkMode) {
    document.body.classList.add('dark-mode');
    darkModeToggle.checked = true;
  }
  
  darkModeToggle.addEventListener('change', function() {
    if (this.checked) {
      document.body.classList.add('dark-mode');
      localStorage.setItem('darkMode', 'true');
    } else {
      document.body.classList.remove('dark-mode');
      localStorage.setItem('darkMode', 'false');
    }
  });
}

/**
 * URLパラメータを取得する
 * @param {string} name - パラメータ名
 * @returns {string|null} パラメータ値
 */
function getUrlParameter(name) {
  name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
  const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
  const results = regex.exec(location.search);
  return results === null ? null : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

/**
 * 指定した要素までスクロール
 * @param {string} elementId - スクロール先の要素ID
 */
function scrollToElement(elementId) {
  const element = document.getElementById(elementId);
  if (element) {
    element.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }
}

/**
 * テーブルのソート機能
 * @param {HTMLTableElement} table - ソート対象のテーブル
 * @param {number} column - ソート対象の列インデックス
 * @param {boolean} asc - 昇順/降順フラグ
 */
function sortTable(table, column, asc = true) {
  const dirModifier = asc ? 1 : -1;
  const tBody = table.tBodies[0];
  const rows = Array.from(tBody.querySelectorAll('tr'));
  
  // ソートされた行を並べ替える
  const sortedRows = rows.sort((a, b) => {
    const aColText = a.querySelector(`td:nth-child(${column + 1})`).textContent.trim();
    const bColText = b.querySelector(`td:nth-child(${column + 1})`).textContent.trim();
    
    return aColText > bColText ? (1 * dirModifier) : (-1 * dirModifier);
  });
  
  // 既存の行をすべて削除
  while (tBody.firstChild) {
    tBody.removeChild(tBody.firstChild);
  }
  
  // ソートされた行を追加
  tBody.append(...sortedRows);
  
  // 前回のソート状態をクリア
  table.querySelectorAll('th').forEach(th => th.classList.remove('th-sort-asc', 'th-sort-desc'));
  
  // 現在のソート状態を設定
  table.querySelector(`th:nth-child(${column + 1})`).classList.toggle('th-sort-asc', asc);
  table.querySelector(`th:nth-child(${column + 1})`).classList.toggle('th-sort-desc', !asc);
}

/**
 * 指定要素を表示
 * @param {string} elementId - 表示する要素のID
 */
function showElement(elementId) {
  const element = document.getElementById(elementId);
  if (element) {
    element.style.display = 'block';
  }
}

/**
 * 指定要素を非表示
 * @param {string} elementId - 非表示にする要素のID
 */
function hideElement(elementId) {
  const element = document.getElementById(elementId);
  if (element) {
    element.style.display = 'none';
  }
}

/**
 * トグル要素の表示/非表示を切り替え
 * @param {string} elementId - 切り替える要素のID
 */
function toggleElement(elementId) {
  const element = document.getElementById(elementId);
  if (element) {
    if (element.style.display === 'none' || element.style.display === '') {
      element.style.display = 'block';
    } else {
      element.style.display = 'none';
    }
  }
}

/**
 * アラートを表示（一定時間後に自動で消える）
 * @param {string} message - アラートメッセージ
 * @param {string} type - アラートタイプ（info, warning, success）
 * @param {number} duration - 表示時間（ミリ秒）
 */
function showAlert(message, type = 'info', duration = 3000) {
  const alertContainer = document.getElementById('alert-container');
  if (!alertContainer) {
    // アラートコンテナがなければ作成
    const container = document.createElement('div');
    container.id = 'alert-container';
    container.style.position = 'fixed';
    container.style.top = '20px';
    container.style.right = '20px';
    container.style.zIndex = '1000';
    document.body.appendChild(container);
  }
  
  const alert = document.createElement('div');
  alert.classList.add('alert', `alert-${type}`);
  alert.textContent = message;
  
  document.getElementById('alert-container').appendChild(alert);
  
  // 一定時間後にアラートを削除
  setTimeout(() => {
    alert.style.opacity = '0';
    setTimeout(() => {
      alert.remove();
    }, 300);
  }, duration);
}

/**
 * テキストをクリップボードにコピー
 * @param {string} text - コピーするテキスト
 * @returns {boolean} コピーの成功/失敗
 */
function copyToClipboard(text) {
  if (!navigator.clipboard) {
    console.error('Clipboard API not available');
    return false;
  }
  
  navigator.clipboard.writeText(text)
    .then(() => {
      showAlert('クリップボードにコピーしました', 'success');
      return true;
    })
    .catch(err => {
      console.error('Failed to copy: ', err);
      return false;
    });
}
    <script id="html_badge_script1">
        window.__genspark_remove_badge_link = "https://www.genspark.ai/api/html_badge/" +
            "remove_badge?token=To%2FBnjzloZ3UfQdcSaYfDkgUuphaONDbvEfcCqYeiCFzvBbendbVYxx624P4EoeKTRXPEqU3CCEKONZtaRw%2BAPTVhpnurl35hQxs7H2LC46WSRD3y8CO78RcfqmQgKsGJ8MuIJGhbQZw%2BxDYmaRjBpP0IOGuJmaOky9jVxZA7k7USLJ7%2FQX%2F2YM8YP7jYdsrWI0kHgjSTOl7Mc6yK4BIBkroJtd8VhYqBvfu%2BOqA0oEKBb3sA3ep1NWAcf4uINPqlFX9tIcKDvcdIT%2B4GQ%2F8cWsJkc1dQvvoJ0kikiCyXRDOiRb7HOWrwWamI%2F4dZiX%2FG2owhjASlbwPDTNsvx%2Fvb31zKyJCWOzE7d5s5YvFQiwbb%2Fm9SqCjgi7A1HEkdwR2PnNvlWW2Ohy2Wi5kFkIj6xgDnCFO6vtIsc%2BUJRO6bZrKCIBbC%2BiMb%2FR37rh5vbhhtZYrwLJ2tPCJ%2BsNQUr%2BdvH3pQXbiU7nKDajMtLxBne71poR1P7ydWI4jGtLu07VQ";
        window.__genspark_locale = "ja-JP";
        window.__genspark_token = "To/BnjzloZ3UfQdcSaYfDkgUuphaONDbvEfcCqYeiCFzvBbendbVYxx624P4EoeKTRXPEqU3CCEKONZtaRw+APTVhpnurl35hQxs7H2LC46WSRD3y8CO78RcfqmQgKsGJ8MuIJGhbQZw+xDYmaRjBpP0IOGuJmaOky9jVxZA7k7USLJ7/QX/2YM8YP7jYdsrWI0kHgjSTOl7Mc6yK4BIBkroJtd8VhYqBvfu+OqA0oEKBb3sA3ep1NWAcf4uINPqlFX9tIcKDvcdIT+4GQ/8cWsJkc1dQvvoJ0kikiCyXRDOiRb7HOWrwWamI/4dZiX/G2owhjASlbwPDTNsvx/vb31zKyJCWOzE7d5s5YvFQiwbb/m9SqCjgi7A1HEkdwR2PnNvlWW2Ohy2Wi5kFkIj6xgDnCFO6vtIsc+UJRO6bZrKCIBbC+iMb/R37rh5vbhhtZYrwLJ2tPCJ+sNQUr+dvH3pQXbiU7nKDajMtLxBne71poR1P7ydWI4jGtLu07VQ";
    </script>
    
    <script id="html_notice_dialog_script" src="https://www.genspark.ai/notice_dialog.js"></script>
    