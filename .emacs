;;; -*- emacs-lisp -*-

;;; default .emacs for Yokota Lab.
;;;
;;; $Id: .emacs,v 1.1 1998/04/08 09:28:20 kunishi Exp kunishi $
;;;

;; User Setting
;; nn
(setq enable-double-n-syntax t)
;; BS <-> DEL
;(load-library "term/bobcat")
;; non openning message
(setq inhibit-startup-message t)
;; display clock time
(display-time)
;; print line number
(setq line-number-mode t)
;; ALT+g , goto line number
;(define-key esc-map "g" 'goto-line)

;; key binding
(global-set-key "\M-g" 'goto-line)
(global-set-key "\M-=" 'goto-line)
(global-set-key "\M-@" 'set-mark-command)
(global-set-key "\M-\C-m" 'compile)
(global-set-key "\C-xc" 'compile)
(global-set-key "\C-h" 'backward-delete-char)

;; tools for MIME (tm)
;; if you use Mew as your mailreader, comment out all lines in this part.
;(require 'mime-setup)
;; end tm part

;; MH 
;; if you use Mew as your mailreader, comment out all lines in this part.
(global-set-key "\C-xm" 'mh-smail)
(global-set-key "\C-x4m" 'mh-smail-other-window)
(global-set-key "\C-xr" 'mh-rmail)
;(setq mh-progs "/usr/local/bin/mh")
(setq mh-lib "/usr/local/lib/mh")
(setq mh-ins-buf-prefix "> ")
(setq mhl-formfile "/usr/local/lib/mh/mhl.format")
(if (> (string-to-int emacs-version) 18)
    (progn
      (define-program-coding-system nil ".*Forms.*" *junet*)
      (define-program-coding-system nil ".*scan.*" *junet*)
      (define-program-coding-system nil ".*inc.*" *junet*)
      (define-program-coding-system nil ".*mhl.*" *junet*)
      (define-program-coding-system nil ".*anno.*" *junet*)
      (define-program-coding-system nil ".*rcvstore.*" *junet*)
      (setq mh-lpr-command-format "a2ps '%s' | lp")
      (setq mh-compose-letter-function
	    '(lambda (to subject cc) (set-file-coding-system *junet*)))))
;; end MH part

;;; Mew
(setq load-path
      (cons "/usr/local/lib/mule/site-lisp/mew" load-path))
(autoload 'mew "mew" nil t)
(autoload 'mew-send "mew" nil t)
(setq mew-mail-domain-list '("c.oka-pu.ac.jp"))
(setq mew-icon-directory "/usr/local/share/emacs/etc/mew")
;; If you use XEmacs and your video chip provides only limited
;; color map(e.g. 256), put the following line to avoid exhaustion
;; of colors.
;(setq mew-demo-picture nil)
(cond
 ((string-match "XEmacs" emacs-version)
;  (setq url-be-asynchronous t)
;  (setq-default buffer-file-coding-system 'iso-2022-jp)
;  (setq keyboard-coding-system    'iso-2022-jp)
;  (setq terminal-coding-system    'iso-2022-jp)
  (add-menu-item '("Apps") "Read Mail (Mew)" 'mew t "Read Mail (VM)...")
  (add-menu-item '("Apps") "Send Mail (Mew)" 'mew-send t "Read Mail (VM)...")
  (delete-menu-item '("Apps" "Read Mail (VM)..."))
  (delete-menu-item '("Apps" "Read Mail (MH)..."))
  (delete-menu-item '("Apps" "Send mail..."))
  (setq toolbar-mail-reader 'Mew)
  (setq toolbar-mail-commands-alist
	(cons '(Mew . mew) toolbar-mail-commands-alist))
  )
 ((string< "20" emacs-version)
;  (setq standard-fontset-spec14
;	"-*-fixed-medium-r-normal-*-14-*-*-*-*-*-fontset-standard")
;  (create-fontset-from-fontset-spec standard-fontset-spec14 nil 'noerror)
;  (set-default-font standard-fontset-spec14)
  (setup-japanese-environment)
  )
 (t ;; Mule 2.3 or Emacs 19
  )
 )
;;; Citation tip
(setq mew-cite-fields '("From:" "Subject:" "Date:" "Message-ID:"))
(setq mew-cite-format "From: %s\nSubject: %s\nDate: %s\nMessage-ID: %s\n\n")
(setq mew-cite-prefix-function 'mew-cite-prefix-username)
;;; ispell-message for Mew
;;;	You should apply ispell.el.patch to ispell.el.
;(add-hook 'mew-send-hook 'ispell-message)
;; end Mew part

;; GNUS
(setq gnus-nntp-server "compub")
(if (> (string-to-int emacs-version) 18)
    (define-service-coding-system "nntp" nil *junet*unix))
(setq gnus-local-domain "c.oka-pu.ac.jp")
(setq gnus-local-organization
      "Faculty of Computer Science and System Enginerring, Okayama Prefectural Univ., JAPAN")  
(setq gnus-local-timezone "+900")
(setq gnus-use-generic-from t)
(setq gnus-use-generic-path t)

(setq gnus-mail-reply-method
      (function gnus-mail-reply-using-mhe))
(setq gnus-mail-other-window-method
      (function gnus-mail-other-window-using-mhe))
(if (> (string-to-int emacs-version) 18)
    (setq gnus-default-article-saver
      (function gnus-summary-save-in-folder)))
(setq gnus-author-copy
      "|/usr/local/lib/mh/rcvstore +Article")
(setq gnus-secondary-servers '((":Mail")))
;;; end GNUS part.

;; bobcat
(load "term/bobcat")

;; jserver
;(set-jserver-host-name "chi")

;; boiled-egg
;(load "boiled-egg")
;(global-set-key "\C-x\C-j" 'boil)

(cond
 ((and window-system (string-match "^19" emacs-version))
      (setq hilit-mode-enable-list  nil
	    hilit-background-mode   'light
	    hilit-inhibit-hooks     nil
	    hilit-inhibit-rebinding nil)
      (require 'hilit19)
      ;;
      (add-hook 'mew-message-hook
		'hilit-rehighlight-buffer-quietly)
      (hilit-set-mode-patterns 
       '(mew-message-mode)
       '(("^Subject:.*$" nil msg-subject)
	 ("^From:.*$" nil msg-from)
	 ("^X-.*:.*$" nil msg-quote)
	 ("^>.*$" nil msg-quote)
	 ("^[A-Za-z][A-Za-z0-9-]+:" nil msg-header)))
      ;;
      (add-hook 'mew-draft-mode-hook
		'hilit-rehighlight-buffer-quietly)
      (hilit-set-mode-patterns 
       '(mew-draft-mode)
       '(("^Subject:.*$" nil msg-subject)
	 ("^From:.*$" nil msg-from)
	 ("^>.*$" nil msg-quote)
	 ("^[A-Za-z][A-Za-z0-9-]+:" nil msg-header)))
      )
 )

;(setq display-time-day-and-date t)
;(display-time)
;(setq text-mode-hook 'turn-on-auto-fill)

(if (> (string-to-int emacs-version) 18)
    (progn
      (set-default-file-coding-system *euc-japan*)
      (set-display-coding-system *euc-japan*)
      (set-file-coding-system *euc-japan*)
      (setq default-mc-flag t)
      (setq mc-verbose-code t)))
