#ifndef _MENUSTATE_H
#define _MENUSTATE_H

#include "GameState.h"

class MenuState : public GameState
{
protected:
    typedef void(*Callback) ();

    std::vector<Callback> m_callbacks; // If we want to load from XMLs, also needs SetCallbacks function
};

#endif
