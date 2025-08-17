import { test, expect } from '@playwright/test';

test('ダッシュボードページの表示とログアウト操作を検証する', async ({ page }) => {
  // ページ遷移
  await page.goto('dashboard');
  await expect(page).toHaveURL(/.*\/dashboard/);

  // 要素表示
  await expect(page.getByRole('heading', { name: 'ダッシュボード' })).toBeVisible();

  // 主要操作: ログアウトリンクの動作確認
  await page.getByRole('link', { name: 'ログアウト' }).click();
  await expect(page).toHaveURL(/\/login/);
});
