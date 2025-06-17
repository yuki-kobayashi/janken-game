function togglePassword(fieldId, iconElement) {
    const passwordField = document.getElementById(fieldId);
    const icon = iconElement.querySelector('i');

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
    jankenForm.replaceWith(animationArea);

    setTimeout(() => {
        window.location.href = `/play?hand=${encodeURIComponent(hand)}`;
    }, 2000);
}