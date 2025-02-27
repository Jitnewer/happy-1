export class Network {
  constructor (id, title, dateTime, image, firstParagraph, paragraphs, theme) {
    this.id = id
    this.title = title
    this.dateTime = dateTime
    this.image = image
    this.firstParagraph = firstParagraph
    this.paragraphs = paragraphs
    this.theme = theme
  }

  static copyConstructor (network) {
    if (!network) return null
    return new Network(
      network.id,
      network.title,
      network.dateTime,
      network.image,
      network.firstParagraph,
      network.paragraphs,
      network.theme
    )
  }
}
