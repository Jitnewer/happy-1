export class User {
  static userTypes = {
    Admin: 'ADMIN',
    Entrepreneur: 'ENTREPRENEUR',
    Partner: 'PARTNER',
    SuperUser: 'SUPERUSER'
  };

  static status = {
    Active: 'ACTIVE',
    Inactive: 'INACTIVE',
    Banned: 'BANNED',
    Unbanned: 'UNBANNED'
  }

  constructor (id, userEvents, profilePic, firstname, lastname, mail, gender, age, companyType, tag, status, userType, postalCode, password, companyName) {
    this.id = id
    this.profilePic = profilePic
    this.firstname = firstname
    this.lastname = lastname
    this.mail = mail
    this.gender = gender
    this.age = age
    this.companyType = companyType
    this.tag = tag
    this.status = status
    this.userType = userType
    this.postalCode = postalCode
    this.password = password
    this.companyName = companyName
  }

  static copyConstructor (user) {
    if (!user) return null

    return new User(
      user.id,
      user.userEvents,
      user.profilePic,
      user.firstname,
      user.lastname,
      user.mail,
      user.gender,
      user.age,
      user.companyType,
      user.tag,
      user.status,
      user.userType,
      user.postalCode,
      user.password,
      user.companyName
    )
  }

  static createSampleUser (userId) {
    const id = userId
    const image = 'assets/profilePic/profilepic.png'

    const firstname = ['Tim', 'Mylo', 'Pim', 'Eva']
    const lastname = ['Groot', 'Commandeur', 'Voortuin', 'Mase']
    const names = ['TimGroot', 'Mylo', 'Pim', 'Eva']
    const randomIndex = Math.floor(Math.random() * names.length)
    const randomName = names[randomIndex]

    const userTypeValues = User.userTypes.Admin

    const mail = randomName + '@gmail.com'
    const gender = 'male'
    let tag
    if (userTypeValues === 'PARTNER') {
      tag = 'Chain-partner'
    } else {
      tag = 'N/A'
    }

    const randomStatusNumb = Math.random()
    const status = randomStatusNumb < 0.5 ? 'ACTIVE' : 'INACTIVE'
    const age = Math.floor((Math.random() * 50) + 50)
    const companyType = 'Catering'
    const postalCode = '1242 DA'

    return new User(id, [], image, firstname[randomIndex], lastname[randomIndex], mail, gender, age, companyType, tag, status, userTypeValues, postalCode, 'password', companyType)
  }

  static generateId (usedIdList) {
    let newId
    do {
      // Generate a new ID (you can use any method you prefer)
      newId = Math.floor((Math.random() * 100) + 1) // Replace with your preferred method to generate IDs

      // If the newId is in usedIds, repeat the loop to generate a new one
    } while (usedIdList.includes(newId))

    // Once a unique ID is generated, return it
    return newId
  }
}
