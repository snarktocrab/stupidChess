#ifndef _PLAYSTATE_H
#define _PLAYSTATE_H

#include "MenuState.h"
#include "GameObject.h"

class PlayState : public MenuState
{
public:
    virtual void update();
    virtual void render();

    virtual bool onEnter();
    virtual bool onExit();

    virtual std::string getStateID() const { return s_playID; }

private:
    static const std::string s_playID;

    std::vector<GameObject*> m_gameObjects;
};

#endif
