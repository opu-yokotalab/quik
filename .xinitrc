#! /bin/sh
# $Id: xinitrc,v 1.2 1998/07/03 04:03:49 kunishi Exp $

DISPLAY=`hostname`:0; export DISPLAY

MITFONTSDIR=/usr/local/X11/lib/X11/fonts
test -d ${MITFONTSDIR}/misc && xset fp+ ${MITFONTSDIR}/misc/
test -d ${MITFONTSDIR}/75dpi && xset fp+ ${MITFONTSDIR}/75dpi/
test -d ${MITFONTSDIR}/Type1 && xset fp+ ${MITFONTSDIR}/Type1/
test -d ${MITFONTSDIR}/100dpi && xset fp+ ${MITFONTSDIR}/100dpi/
test -d ${MITFONTSDIR}/Speedo && xset fp+ ${MITFONTSDIR}/Speedo/
test -d ${MITFONTSDIR}/PEX && xset fp+ ${MITFONTSDIR}/PEX/
test -d ${MITFONTSDIR}/waseda && xset fp+ ${MITFONTSDIR}/waseda/

userresources=${HOME}/.Xdefaults
usermodmap=${HOME}/.Xmodmap
if [ -f ${userresources} ]; then
    xrdb -merge ${userresources}
fi
if [ -f ${usermodmap} ]; then
    xmodmap ${usermodmap}
fi

### start some nice programs
### tune up by yourself!!
#twm &
fvwm95-2 &
#oclock -geometry 50x50-1+1 &
#xwnmo &
#kinput2 &
kterm -n console -T console -sb -geom +0+0 -C
### end of tune up part.

# post processing for X
if [ "`/usr/local/dblab/bin/machine`" = 'sunos' ]; then
	clear_colormap
fi
