#ifndef _PAUSESTATE_H
#define _PAUSESTATE_H

#include "MenuState.h"
#include "GameObject.h"
#include "MenuButton.h"

class PauseState : public MenuState
{
public:
    virtual void update();
    virtual void render();

    virtual bool onEnter();
    virtual bool onExit();

    virtual std::string getStateID() const { return s_pauseID; }

private:
    // Call back functions for menu items
    static void s_pauseToMain();
    static void s_resumePlay();

    static const std::string s_pauseID;

    std::vector<GameObject*> m_gameObjects;
};

#endif
