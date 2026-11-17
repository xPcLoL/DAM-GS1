/* ========================================
   PORTFOLIO - ANIMACIONES ANIME.JS v3
   ======================================== */

// ===== MOBILE NAV =====
const navToggle = document.getElementById('nav-toggle');
const navLinks = document.querySelector('.nav-links');

navToggle.addEventListener('click', () => {
    navToggle.classList.toggle('active');
    navLinks.classList.toggle('active');
});

document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', () => {
        navToggle.classList.remove('active');
        navLinks.classList.remove('active');
    });
});

// ===== HERO PARTICLES =====
function createParticles() {
    const container = document.getElementById('hero-particles');
    const count = 60;
    for (let i = 0; i < count; i++) {
        const p = document.createElement('div');
        p.classList.add('particle');
        p.style.left = Math.random() * 100 + '%';
        p.style.top = Math.random() * 100 + '%';
        container.appendChild(p);
    }

    anime({
        targets: '.particle',
        opacity: [
            { value: [0, 0.6], duration: 1000 },
            { value: 0, duration: 1000 }
        ],
        translateY: function () { return [0, -100 - Math.random() * 200]; },
        translateX: function () { return [-30 + Math.random() * 60, -30 + Math.random() * 60]; },
        scale: [
            { value: [0.5, 1.5], duration: 1000 },
            { value: 0.5, duration: 1000 }
        ],
        duration: function () { return 3000 + Math.random() * 4000; },
        delay: anime.stagger(80, { from: 'center' }),
        loop: true,
        easing: 'linear'
    });
}
createParticles();

// ===== TERMINAL TYPING EFFECT =====
function startTypingAnimation() {
    const nameText = 'Tu Nombre';
    const subtitleText = '> Full Stack Developer | Estudiante DAM';
    const roleText = '> Apasionado por el código y la innovación';

    const nameEl = document.getElementById('typed-name');
    const subtitleEl = document.getElementById('typed-subtitle');
    const roleEl = document.getElementById('typed-role');
    const heroCtaEl = document.getElementById('hero-cta');

    let charIndex = 0;

    function typeName() {
        if (charIndex < nameText.length) {
            nameEl.textContent += nameText[charIndex];
            charIndex++;
            setTimeout(typeName, 80 + Math.random() * 60);
        } else {
            setTimeout(typeSubtitle, 400);
        }
    }

    function typeSubtitle() {
        subtitleEl.style.opacity = '1';
        let i = 0;
        const outputEl = subtitleEl.querySelector('.output-text');
        function type() {
            if (i < subtitleText.length) {
                outputEl.textContent += subtitleText[i];
                i++;
                setTimeout(type, 30 + Math.random() * 20);
            } else {
                setTimeout(typeRole, 300);
            }
        }
        type();
    }

    function typeRole() {
        roleEl.style.opacity = '1';
        let i = 0;
        const outputEl = roleEl.querySelector('.output-text');
        function type() {
            if (i < roleText.length) {
                outputEl.textContent += roleText[i];
                i++;
                setTimeout(type, 25 + Math.random() * 15);
            } else {
                setTimeout(() => {
                    anime({
                        targets: heroCtaEl,
                        opacity: [0, 1],
                        translateY: [20, 0],
                        duration: 800,
                        easing: 'easeOutExpo'
                    });
                }, 300);
            }
        }
        type();
    }

    setTimeout(typeName, 800);
}
startTypingAnimation();

// ===== STAGGER GRID (anime.js homepage style) =====
function createStaggerGrid() {
    const grid = document.getElementById('stagger-grid');
    const cols = window.innerWidth < 600 ? 15 : window.innerWidth < 968 ? 20 : 30;
    const rows = 10;
    const total = cols * rows;

    grid.innerHTML = '';
    grid.style.gridTemplateColumns = 'repeat(' + cols + ', 1fr)';

    for (let i = 0; i < total; i++) {
        const dot = document.createElement('div');
        dot.classList.add('stagger-dot');
        grid.appendChild(dot);
    }

    const observer = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
            if (entry.isIntersecting) {
                playStaggerAnimation(cols, rows);
                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.3 });

    observer.observe(grid);
}

function playStaggerAnimation(cols, rows) {
    anime({
        targets: '.stagger-dot',
        scale: [
            { value: 1.4, duration: 400, easing: 'easeOutQuad' },
            { value: 1, duration: 600, easing: 'easeInOutQuad' }
        ],
        opacity: [
            { value: 1, duration: 400, easing: 'easeOutQuad' },
            { value: 0.15, duration: 600, easing: 'easeInOutQuad' }
        ],
        backgroundColor: [
            { value: '#00d4ff', duration: 400 },
            { value: '#00ff41', duration: 600 }
        ],
        borderRadius: [
            { value: '20%', duration: 400 },
            { value: '50%', duration: 600 }
        ],
        delay: anime.stagger(30, {
            grid: [cols, rows],
            from: 'center'
        }),
        duration: 1000,
        easing: 'easeOutQuad',
        loop: true,
        direction: 'normal'
    });
}

createStaggerGrid();

// ===== SCROLL REVEAL ANIMATIONS =====
function setupScrollReveals() {
    const revealElements = document.querySelectorAll('.reveal-element');

    const observer = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
            if (entry.isIntersecting) {
                anime({
                    targets: entry.target,
                    opacity: [0, 1],
                    translateY: [40, 0],
                    duration: 900,
                    easing: 'easeOutExpo',
                    delay: 100
                });
                observer.unobserve(entry.target);
            }
        });
    }, {
        threshold: 0.15,
        rootMargin: '0px 0px -50px 0px'
    });

    revealElements.forEach(function (el) { observer.observe(el); });
}
setupScrollReveals();

// ===== STAT COUNTERS + BARS =====
function setupStatCounters() {
    const statCards = document.querySelectorAll('.stat-card');

    const observer = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
            if (entry.isIntersecting) {
                const numberEl = entry.target.querySelector('.stat-number');
                const barFill = entry.target.querySelector('.stat-bar-fill');
                const target = parseInt(numberEl.getAttribute('data-target'));
                const barWidth = parseInt(barFill.getAttribute('data-width'));

                const obj = { val: 0 };
                anime({
                    targets: obj,
                    val: [0, target],
                    duration: 1500,
                    easing: 'easeOutExpo',
                    round: 1,
                    update: function () {
                        numberEl.textContent = Math.round(obj.val) + '+';
                    }
                });

                anime({
                    targets: barFill,
                    width: barWidth + '%',
                    duration: 1500,
                    easing: 'easeOutExpo',
                    delay: 200
                });

                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.5 });

    statCards.forEach(function (card) { observer.observe(card); });
}
setupStatCounters();

// ===== SKILL LEVEL BARS =====
function setupSkillBars() {
    const skillBadges = document.querySelectorAll('.skill-badge');

    const observer = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
            if (entry.isIntersecting) {
                const fill = entry.target.querySelector('.skill-level-fill');
                const level = parseInt(fill.getAttribute('data-level'));

                anime({
                    targets: fill,
                    width: level + '%',
                    duration: 1200,
                    easing: 'easeOutExpo',
                    delay: 300
                });

                anime({
                    targets: entry.target,
                    opacity: [0, 1],
                    scale: [0.8, 1],
                    duration: 600,
                    easing: 'easeOutExpo'
                });

                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.2 });

    skillBadges.forEach(function (badge) { observer.observe(badge); });
}
setupSkillBars();

// ===== FLOATING LANGUAGE TAGS =====
function setupFloatingTags() {
    const tags = document.querySelectorAll('.float-tag');

    anime({
        targets: '.float-tag',
        translateY: function () { return [-12 + Math.random() * 24, 12 - Math.random() * 24]; },
        translateX: function () { return [-5 + Math.random() * 10, 5 - Math.random() * 10]; },
        rotate: function () { return [-3 + Math.random() * 6, 3 - Math.random() * 6]; },
        scale: [
            { value: 1.05, duration: 1500 },
            { value: 0.95, duration: 1500 }
        ],
        delay: anime.stagger(150, { from: 'center' }),
        duration: 3000,
        loop: true,
        direction: 'alternate',
        easing: 'easeInOutQuad'
    });

    tags.forEach(function (tag) {
        tag.addEventListener('mouseenter', function () {
            anime({
                targets: tag,
                scale: 1.3,
                duration: 300,
                easing: 'easeOutExpo'
            });
            tag.style.textShadow = '0 0 15px currentColor';
            tag.style.boxShadow = '0 0 20px rgba(0,255,65,0.3)';
        });
        tag.addEventListener('mouseleave', function () {
            anime({
                targets: tag,
                scale: 1,
                duration: 300,
                easing: 'easeOutExpo'
            });
            tag.style.textShadow = 'none';
            tag.style.boxShadow = 'none';
        });
    });
}
setupFloatingTags();

// ===== ORBIT RINGS ANIMATION =====
function setupOrbitAnimation() {
    const orbitContainer = document.getElementById('skills-orbit');
    if (!orbitContainer) return;

    for (let r = 0; r < 3; r++) {
        const ring = document.createElement('div');
        ring.style.cssText =
            'position: absolute;' +
            'width: ' + (160 + r * 70) + 'px;' +
            'height: ' + (160 + r * 70) + 'px;' +
            'border: 1px solid rgba(0, 212, 255, ' + (0.15 - r * 0.03) + ');' +
            'border-radius: 50%;';
        ring.classList.add('orbit-ring');
        orbitContainer.appendChild(ring);
    }

    anime({
        targets: '.orbit-ring',
        rotate: [0, 360],
        duration: function () { return 8000 + Math.random() * 6000; },
        loop: true,
        easing: 'linear',
        delay: anime.stagger(500)
    });

    anime({
        targets: '.orbit-center',
        scale: [1, 1.05, 1],
        duration: 3000,
        loop: true,
        easing: 'easeInOutQuad'
    });
}
setupOrbitAnimation();

// ===== PROJECT CARDS HOVER ANIMATION =====
function setupProjectCards() {
    const cards = document.querySelectorAll('.project-card');

    cards.forEach(function (card) {
        card.addEventListener('mouseenter', function () {
            anime({
                targets: card.querySelector('.project-folder'),
                rotate: [0, -15, 15, 0],
                scale: 1.2,
                duration: 500,
                easing: 'easeOutExpo'
            });
        });
        card.addEventListener('mouseleave', function () {
            anime({
                targets: card.querySelector('.project-folder'),
                scale: 1,
                rotate: 0,
                duration: 300,
                easing: 'easeOutQuad'
            });
        });
    });
}
setupProjectCards();

// ===== NAVBAR SCROLL EFFECT =====
window.addEventListener('scroll', function () {
    const navbar = document.getElementById('navbar');
    const scrollPos = window.scrollY;

    if (scrollPos > 100) {
        navbar.style.borderBottomColor = 'rgba(0, 255, 65, 0.15)';
        navbar.style.boxShadow = '0 2px 20px rgba(0, 0, 0, 0.3)';
    } else {
        navbar.style.borderBottomColor = '#1a1a2e';
        navbar.style.boxShadow = 'none';
    }

    var scrollIndicator = document.getElementById('scroll-indicator');
    if (scrollIndicator) {
        scrollIndicator.style.opacity = scrollPos > 50 ? '0' : '1';
    }
});

// ===== CONTACT FORM ANIMATION =====
var contactForm = document.getElementById('contact-form');
if (contactForm) {
    contactForm.addEventListener('submit', function (e) {
        e.preventDefault();
        var btn = contactForm.querySelector('.btn-terminal');
        var originalText = btn.textContent;
        btn.textContent = '> enviando...';
        btn.style.color = '#ffdd00';
        btn.style.borderColor = '#ffdd00';

        anime({
            targets: btn,
            scale: [1, 0.95, 1],
            duration: 300,
            easing: 'easeInOutQuad'
        });

        setTimeout(function () {
            btn.textContent = '> ¡mensaje_enviado! ✓';
            btn.style.color = '#00ff41';
            btn.style.borderColor = '#00ff41';

            anime({
                targets: btn,
                scale: [0.95, 1.05, 1],
                duration: 500,
                easing: 'easeOutExpo'
            });

            setTimeout(function () {
                btn.textContent = originalText;
                btn.style.color = '';
                btn.style.borderColor = '';
                contactForm.reset();
            }, 2500);
        }, 1500);
    });
}

// ===== GLITCH EFFECT ON SECTION TITLES =====
function setupGlitchEffect() {
    var titles = document.querySelectorAll('.section-title');

    titles.forEach(function (title) {
        title.addEventListener('mouseenter', function () {
            anime({
                targets: title,
                translateX: [
                    { value: -3, duration: 50 },
                    { value: 3, duration: 50 },
                    { value: -2, duration: 50 },
                    { value: 2, duration: 50 },
                    { value: 0, duration: 50 }
                ],
                easing: 'linear'
            });

            title.style.textShadow = '2px 0 #ff00ff, -2px 0 #00d4ff';
            setTimeout(function () {
                title.style.textShadow = 'none';
            }, 250);
        });
    });
}
setupGlitchEffect();

// ===== SMOOTH SCROLL FOR NAV LINKS =====
document.querySelectorAll('a[href^="#"]').forEach(function (anchor) {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        var target = document.querySelector(this.getAttribute('href'));
        if (target) {
            var offsetTop = target.offsetTop - 60;
            window.scrollTo({
                top: offsetTop,
                behavior: 'smooth'
            });
        }
    });
});

// ===== MATRIX RAIN (subtle background) =====
function createMatrixRain() {
    var canvas = document.createElement('canvas');
    canvas.id = 'matrix-canvas';
    canvas.style.cssText =
        'position: fixed; top: 0; left: 0; width: 100%; height: 100%;' +
        'pointer-events: none; z-index: 0; opacity: 0.03;';
    document.body.prepend(canvas);

    var ctx = canvas.getContext('2d');
    var w, h, columns, drops;

    function resize() {
        w = canvas.width = window.innerWidth;
        h = canvas.height = window.innerHeight;
        columns = Math.floor(w / 16);
        drops = [];
        for (var i = 0; i < columns; i++) drops[i] = 1;
    }
    resize();
    window.addEventListener('resize', resize);

    var chars = 'アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲン0123456789ABCDEF<>/{}[];';

    function draw() {
        ctx.fillStyle = 'rgba(10, 10, 10, 0.06)';
        ctx.fillRect(0, 0, w, h);
        ctx.fillStyle = '#00ff41';
        ctx.font = '14px Fira Code, monospace';

        for (var i = 0; i < drops.length; i++) {
            var text = chars[Math.floor(Math.random() * chars.length)];
            ctx.fillText(text, i * 16, drops[i] * 16);
            if (drops[i] * 16 > h && Math.random() > 0.975) {
                drops[i] = 0;
            }
            drops[i]++;
        }
        requestAnimationFrame(draw);
    }
    draw();
}
createMatrixRain();

console.log('%c> Portfolio cargado correctamente ✓', 'color: #00ff41; font-family: Fira Code; font-size: 14px;');
