#include "SDLGameObject.h"
#include "Game.h"

SDLGameObject::SDLGameObject(const LoaderParams* pParams) :
GameObject(), m_position(0, 0),
m_velocity(0, 0), m_acceleration(0, 0)
{
    m_position = Vector2D(pParams->getX(), pParams->getY());
    m_width = pParams->getWidth();
    m_height = pParams->getHeight();
    m_textureID = pParams->getTextureID();
    m_currentRow = 1;
    m_currentFrame = 1;
    m_numFrames = pParams->getNumFrames();
}

void SDLGameObject::draw()
{

}

void SDLGameObject::clean()
{

}

void SDLGameObject::update()
{
	m_velocity += m_acceleration;
	m_position += m_velocity;
}