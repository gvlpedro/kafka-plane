package usecase.model

import java.net.URI
import java.time.Instant

case class User(id: Id[User], updatedOn: Instant, image: URI, nickname: String, verified: Boolean, deleted: Boolean)

object User {
  implicit val record: Record[Id[User], User] = new Record[Id[User], User] {
    val topic                       = "users"
    def key(user: User): Id[User]   = user.id
    def timestamp(user: User): Long = user.updatedOn.toEpochMilli
  }
}
