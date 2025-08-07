const { execSync } = require('child_process');
const fs = require('fs');
const path = require('path');

function ensurePsql() {
  try {
    execSync('psql --version', { stdio: 'ignore' });
    return;
  } catch (e) {
    const winPath = path.join('C:', 'Program Files', 'PostgreSQL', '15', 'bin');
    if (fs.existsSync(winPath)) {
      process.env.PATH += `;${winPath}`;
      try {
        execSync('psql --version', { stdio: 'ignore' });
        return;
      } catch (err) {
        console.error('PostgreSQL 15 がインストールされていません。');
        process.exit(1);
      }
    } else {
      console.error('PostgreSQL 15 がインストールされていません。');
      process.exit(1);
    }
  }
}

function run(cmd) {
  console.log(`\n$ ${cmd}`);
  execSync(cmd, { stdio: 'inherit' });
}

ensurePsql();

if (process.platform === 'win32') {
  try {
    execSync('chcp 65001');
  } catch (e) {
    console.warn('Failed to change code page to UTF-8');
  }
}

run('docker-compose up -d --build');
try {
  run('npm install');
  run('npx playwright install');
  run('npm run test:e2e');
} finally {
  run('docker-compose down');
}
