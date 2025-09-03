export function getCsrfToken() {
    const meta = document.querySelector('meta[name="_csrf"]');
    if (meta) {
        return meta.getAttribute('content');
    }
    const match = document.cookie.match(/XSRF-TOKEN=([^;]+)/);
    return match ? decodeURIComponent(match[1]) : null;
}
