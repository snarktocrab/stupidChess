#ifndef _MENUBUTTON_H
#define _MENUBUTTON_H

#include "SDLGameObject.h"

class MenuButton : public SDLGameObject
{
public:
    virtual void draw();
    virtual void update();
    virtual void clean();

    void setCallback(void(*callback)()) { m_callback = callback; }
    int getCallbackID() { return m_callbackID; }

    MenuButton(const LoaderParams* pParams, void (*callback)());

private:
    enum button_state
    {
        MOUSE_OUT, MOUSE_OVER, CLICKED
    };

    void(*m_callback)();

    bool m_bReleased;

    int m_callbackID;
};

#endif
