#ifndef _MAINMENUSTATE_H
#define _MAINMENUSTATE_H

#include "MenuState.h"
#include "GameObject.h"
#include "MenuButton.h"

class MainMenuState : public MenuState
{
public:
    virtual void update();
    virtual void render();

    virtual bool onEnter();
    virtual bool onExit();

    virtual std::string getStateID() const { return s_menuID; }

private:
    // Call back functions for menu items
    static void s_menuToPlay();
    static void s_exitFromMenu();

    static const std::string s_menuID;

    std::vector<GameObject*> m_gameObjects;
};

#endif