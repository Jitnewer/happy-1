import { RESTAdaptorWithFetch } from '@/services/RESTAdaptorWithFetch'
import CustomError from '@/CustomError'

export class CachedRESTAdaptorWithFetch extends RESTAdaptorWithFetch {
  entities;
  entity

  constructor (resourceUrl, copyConstructor) {
    super(resourceUrl, copyConstructor)
    this.entities = []
    this.entity = null
  }

  async asyncFindAll () {
    try {
      this.entities = await super.asyncFindAll()
      return this.entities
    } catch (error) {
      throw new CustomError('Error in asyncFindAll', error.status || 500, error.error)
    }
  }

  async asyncFindById (id) {
    try {
      const entity = await super.asyncFindById(id)

      // Update or add the entity to the cache

      this.entities = entity

      return entity
    } catch (error) {
      throw new CustomError('Error in asyncFindById', error.status, error.error)
    }
  }

  async asyncSave (data) {
    try {
      const newEntity = await super.asyncSave(data)

      // Add the new entity to the cache
      if (Array.isArray(this.entities)) {
        this.entities.push(newEntity)
      }

      return newEntity
    } catch (error) {
      throw new CustomError('Error in asyncSave', error.status, error.error)
    }
  }

  async asyncSendNewsletter (object) {
    try {
      return await super.asyncSendNewsletter(object)
    } catch (error) {
      throw new CustomError('Error in asyncSendNewsletter', error.toJSON().status || 500, error.toJSON().error)
    }
  }

  async asyncDeleteById (id) {
    // eslint-disable-next-line no-useless-catch
    try {
      // Delete the entity from the server
      return await super.asyncDeleteById(id)
    } catch (error) {
      throw error
    }
  }

  async asyncFindByMail (mail) {
    try {
      return await super.asyncFindByMail(mail)
    } catch (e) {
      throw new CustomError('Error in asyncFindByMail', e.status, e.error)
    }
  }

  async asyncAddEntityToEntity (id1, id2, url) {
    try {
      const response = await super.asyncAddEntityToEntity(id1, id2, url)

      // Invalidate the cache for related entities (assuming a single cache for all entities)
      this.entities = []

      return response
    } catch (error) {
      throw new CustomError('Error in asyncAddEntityToEntity', error.status, error.error)
    }
  }

  async asyncRemoveEntityFromEntity (id1, id2, url) {
    try {
      const isRemoved = await super.asyncRemoveEntityFromEntity(id1, id2, url)

      if (isRemoved) {
        // Invalidate the cache for related entities (assuming a single cache for all entities)
        this.entities = []
      }

      return isRemoved
    } catch (error) {
      throw new CustomError('Error in asyncRemoveEntityFromEntity', error.status, error.error)
    }
  }

  async asyncFindByColumn (column, url) {
    try {
      // Check if data is already in the cache
      if (this.entities.length === 0) {
        this.entities = await super.asyncFindAll()
      }

      // Implement caching logic based on your specific requirements
      const cachedData = this.entities.filter(e => e.column === column)

      if (cachedData.length > 0) {
        return cachedData
      }

      // Fetch data from the server if not in the cache
      const response = await super.asyncFindByColumn(column, url)

      // Update the cache
      this.entities = this.entities.concat(response)

      return response
    } catch (error) {
      throw new CustomError('Error in asyncFindByColumn', error.status, error.error)
    }
  }

  async asyncGetCount (id1, url) {
    try {
      // Check if data is already in the cache
      if (this.entities.length === 0) {
        this.entities = await super.asyncFindAll()
      }

      // Implement caching logic based on your specific requirements
      const cachedData = this.entities.filter(e => e.id === id1)

      if (cachedData.length > 0) {
        return cachedData.length
      }

      // Fetch data from the server if not in the cache
      const response = await super.asyncGetCount(id1, url)

      // Update the cache
      this.entities = this.entities.concat(response)

      return response.length
    } catch (error) {
      throw new CustomError('Error in asyncGetCount', error.status, error.error)
    }
  }

  async asyncFindByProperty (propertyId, url) {
    try {
      // Fetch data from the server if not in the cache
      const response = await super.asyncFindByProperty(propertyId, url)
      // Update the cache
      this.entities = response

      return response
    } catch (error) {
      throw new CustomError('Error in asyncFindByProperty', error.status, error.error)
    }
  }
}
