// パスワード表示切り替え
function togglePassword(fieldId, iconElement) {
    const passwordField = document.getElementById(fieldId);
    const icon = iconElement.querySelector('i');
    // 要素がパスワード入力の場合、テキストに変更
    if (passwordField.type === "password") {
        passwordField.type = "text";
        icon.classList.remove("fa-eye");
        icon.classList.add("fa-eye-slash");
    } else {
        passwordField.type = "password";
        icon.classList.remove("fa-eye-slash");
        icon.classList.add("fa-eye");
    }
}

// じゃんけんアニメーション
function playWithAnimation(hand) {
    const jankenForm = document.querySelector('.janken-form');
    const animationArea = document.createElement('div');
    animationArea.className = 'mt-4';
    animationArea.innerHTML = `
        <h2>じゃんけん…</h2>
        <div style="font-size: 3rem;">✊ ✌️ 🖐️</div>
    `;
    jankenForm.replaceWith(animationArea); // フォームとアニメーションを入れ替えて表示

    setTimeout(() => {
        window.location.href = `/play?hand=${encodeURIComponent(hand)}`;
    }, 2000);
}

// ウェルカム画面アニメーション
function startWelcomeTransition() {
    const box = document.getElementById('welcomeBox');
    if (box) {
        setTimeout(() => {
            box.classList.add('fade-out');
            setTimeout(() => {
                window.location.href = "/";
            }, 1000);
        }, 4000);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    startWelcomeTransition(); // ウェルカム画面に入った際、自動でトップ画面へ遷移
});