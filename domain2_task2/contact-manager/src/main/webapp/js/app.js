/**
 * app.js – Contact Manager client-side helpers
 *
 * Kept deliberately small – the server handles all critical logic;
 * this just polishes the user experience.
 */

'use strict';

// ── Delete confirmation ──────────────────────────────────────────────────────
/**
 * Called by the delete form's onsubmit.
 * Returns false to cancel, true to proceed.
 */
function confirmDelete(contactName) {
    return window.confirm(
        'Are you sure you want to remove "' + contactName + '"?\n' +
        'This cannot be undone.'
    );
}

// ── Toast helper (used programmatically if needed) ────────────────────────────
function showToast(message, type) {
    const toast = document.createElement('div');
    toast.className = 'alert alert-' + (type || 'success');
    toast.style.cssText = [
        'position:fixed',
        'bottom:1.5rem',
        'left:50%',
        'transform:translateX(-50%)',
        'z-index:9999',
        'min-width:260px',
        'text-align:center',
        'box-shadow:0 4px 16px rgba(0,0,0,.15)',
        'pointer-events:none',
    ].join(';');
    toast.innerHTML = '<span class="alert-icon">' +
        (type === 'error' ? '⚠️' : '✅') +
        '</span> ' + message;

    document.body.appendChild(toast);
    setTimeout(function () { toast.classList.add('fade-out'); }, 3000);
    setTimeout(function () { toast.remove(); }, 3600);
}

// ── Table row highlight on fresh-add (via URL hash) ─────────────────────────
document.addEventListener('DOMContentLoaded', function () {
    // If the URL has #new the page just added a row – briefly highlight last row
    if (window.location.hash === '#new') {
        var rows = document.querySelectorAll('.contact-row');
        if (rows.length) {
            var lastRow = rows[rows.length - 1];
            lastRow.style.transition = 'background .4s';
            lastRow.style.background = '#ede9fe';
            setTimeout(function () { lastRow.style.background = ''; }, 1800);
        }
        // Clean the hash without adding a history entry
        history.replaceState(null, '', window.location.pathname + window.location.search);
    }
});
