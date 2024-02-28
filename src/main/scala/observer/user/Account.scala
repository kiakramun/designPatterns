package observer.user

import observer.log.AccountChange.{MailChange, NameChange, ProfilePictureChange}
import observer.log.{Entity, EntityChange, Loggable}
import observer.user.Account.validateMail

import java.net.URI

class Account(
  private val _displayName: String,
  private var _name: String,
  private var _mail: Option[String],
  private var _profilePicture: Option[URI]) extends Entity with Loggable[Entity, EntityChange]:

  def displayName: String = _displayName
  
  def name: String = _name
  
  def mail: String = _mail.getOrElse("")
  
  def profilePicture: Option[URI] = _profilePicture

  def name_=(name: String): Unit = _name = name
    notifyObserver(this, NameChange(name))

  def updateMail(mail: String): Boolean =
    if (validateMail(mail))
      _mail = Some(mail)
      notifyObserver(this, MailChange(mail))
      true
    else
      false

  def updateProfilePicture(uri: URI): Unit = _profilePicture = Some(uri)
    notifyObserver(this,ProfilePictureChange(profilePicture))

  def resetProfilePicture(): Unit = _profilePicture = None
    notifyObserver(this, ProfilePictureChange(None))
  override def identifier: String = _displayName

object Account:
  def apply(displayName: String, name: String, mail: String): Account =
    if (Account.validateMail(mail))
      new Account(displayName, name, Some(mail), None)
    else
      new Account(displayName, name, None, None)

  def apply(displayName: String, name: String, mail: String, profilePicture: URI): Account =
    if (Account.validateMail(mail))
      new Account(displayName, name, Some(mail), Some(profilePicture))
    else
      new Account(displayName, name, None, Some(profilePicture))

  private def validateMail(mail: String): Boolean = mail.matches("(.*)@(.*)\\.(.*)")