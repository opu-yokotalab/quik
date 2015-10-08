# @(#)Cshrc 1.6 91/09/05 SMI
#################################################################
#
#         .cshrc file
#
#         initial setup file for both interactive and noninteractive
#         C-Shells
#
#################################################################

if (`uname -r` =~ 5.*) then
   set owpath=/usr/openwin/bin
else
   set owpath=
endif

# set up search path

set xpath = ( )
set lpath = ( )
set fpath = ( )
set xpath = ( /usr/X11R5/bin )
set lpath = ( /usr/local/bin /usr/local/bin/mh )
set fpath = ( /usr/local/pbmplus )
set apath = ( /usr/local/bin/adm )
set gccpath = ( /usr/local/lib/GNU/GCC/gcc-lib/sparc-sun-sunos4.1.3/2.5.8 )

setenv JDK_HOME /usr/jdk1.1

set path = ($owpath $JDK_HOME/bin . ~ ~/bin /usr/ucb /usr/bin /usr/etc /usr/local/bin /usr/local/etc /usr/bin/X11 /usr/local/X11R5/bin /usr/local/bin/Wnn4 /usr/local/bin/TEX-NTT /usr/local/bin/TEX /usr/games /usr/local/canna/bin $JDK_HOME/bin $xpath $lpath $fpath $apath $gccpath )
#set path = ($path /usr/ccs/bin /usr/openwin/bin /usr/openwin/demo /usr/openwin/bin/xview /usr/openwin/server/modules )
setenv LD_LIBRARY_PATH "/lib:/usr/lib:/usr/X11R5/lib:/usr/X11R5/lib/X11"

# cd path

#set noclobber

# aliases for all shells

alias cd            'cd \!*;echo $cwd'
alias cp            'cp -i'
alias mv            'mv -i'
alias rm            'rm -i'
alias pwd           'echo $cwd'

umask 022

# skip remaining setup if not an interactive shell
#if ($?USER == 0 || $?prompt == 0) exit

# settings  for interactive shells

set history=100
set ignoreeof
#set notify
set savehist=100
set prompt="`hostname`{`whoami`}\!: "
#set time=100

# commands for interactive shells

# other aliases

alias ^L            clear

#alias .             'echo $cwd'
#alias ..            'set dot=$cwd;cd ..'
#alias ,             'cd $dot '

#alias dir          ls
#alias pdw          'echo $cwd'
#alias la           'ls -a'
alias ll            'ls -la'
alias ls            'ls -CFa'
alias h             'history'

#alias help          man
#alias key           'man -k'
alias	xinit	'xinit >&! ~/.xinit-errors ; kbd_mode -a'

# local user setup

#setenv LANG ja_JP.ujis
setenv LANG japanese
setenv XMODIFIERS @im=_XWNMO
setenv MANPATH "/usr/share/man/japanese:/usr/share/man:/usr/X11R5/man/japanese:/usr/X11R5/man:/usr/local/man"
setenv JSERVER localhost
setenv TRRDIR /usr/local/lib/mule/site-lisp/trr

#setenv QUIK_HOME $HOME/Quik
#setenv CLASSPATH .:$QUIK_HOME:$QUIK_HOME/java_quik/commgr:$QUIK_HOME/java_quik/gui:$JDK_HOME/lib/classes.zip

#setenv CLASSPATH .:$HOME/mQuik:$HOME/mQuik/java_quik/commgr:$HOME/mQuik/java_quik/gui:$JDK_HOME/lib/classes.zip

setenv CLASSPATH .:$HOME/Quik:$HOME/Quik/java_quik/commgr:$HOME/Quik/java_quik/gui:$JDK_HOME/lib/classes.zip

#alias netscape '/usr/X11R6/bin/netscape-v301'
alias e emacs
alias m mule
alias net netscape
alias kterm 'kterm -km euc'
#alias gcc   'gcc -o $1 $2'

# for Aglets
setenv AGLET_HOME /usr/local/Aglets1.0.3
set path = ( $path $AGLET_HOME/bin )
setenv CLASSPATH $AGLET_HOME/lib/aglets.jar:$AGLET_HOME/public:$AGLET_HOME/lib:$CLASSPATH
setenv AGLET_PATH $HOME/java/Aglets
setenv AGLET_EXPORT_PATH $AGLET_PATH

#for quik
alias	quik	'java java_quik.Main'
alias	qclient	'java java_quik.Main -client'
alias	qserver	'java java_quik.Main -server'

alias	dir	'ls -laF \!* | more'
alias	cls	clear
alias	del	'\rm -i \!*'
alias	lj	'less \!*java'
alias	km	'kterm -sb -sl 100 &'

