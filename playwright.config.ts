import { defineConfig, devices } from '@playwright/test';
import dotenv from 'dotenv';

dotenv.config();

export default defineConfig({
  testDir: 'e2e',
  use: {
    baseURL: 'http://localhost:8080/giiku',
  },
  globalSetup: require.resolve('./tests/setup.ts'),
  globalTeardown: require.resolve('./tests/teardown.ts'),
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
  ],
});
