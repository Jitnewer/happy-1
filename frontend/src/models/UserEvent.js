export class UserEvent {
  constructor (id, user, event) {
    this.id = id
    this.user = user
    this.event = event
  }

  static copyConstructor (userEvent) {
    if (!userEvent) {
      return new UserEvent(
        userEvent.id,
        userEvent.user,
        userEvent.event
      )
    }
  }
}
