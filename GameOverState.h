#ifndef _GAMEOVERSTATE_H
#define _GAMEOVERSTATE_H

#include "MenuState.h"
#include "GameObject.h"
#include "MenuButton.h"

class GameOverState : public MenuState
{
public:
    virtual void update();
    virtual void render();

    virtual bool onEnter();
    virtual bool onExit();

    virtual std::string getStateID() const { return s_gameOverID; }

private:
    // Call back functions for menu items
    static void s_gameOverToMain();
    static void s_restartPlay();

    static const std::string s_gameOverID;

    std::vector<GameObject*> m_gameObjects;
};

#endif
