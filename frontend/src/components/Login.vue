<template>
  <div class="breadcrum">
    <router-link :to="{ name: 'welcome' }">Home</router-link>
    <p>></p>
    <router-link :to="{ name: 'login' }">Login</router-link>
  </div>
  <div class="sign-up-body">
    <div class="container-sign-up">
      <div class="header-sign-up">Login:</div>
      <div class="form-sign-up-1">
        <form @submit.prevent="login">
          <div class="sign-up-page">
            <div class="title-sign-up-form">Login with your email:</div>
            <div class="field-sign-up">
              <div class="label-sign-up">Email:</div>
              <input v-model="email" type="email" class="sign-up-input" required>
            </div>

            <div class="field-sign-up">
              <div class="label-sign-up">Password:</div>
              <input v-model="password" type="password" class="sign-up-input" required>
            </div>

            <div class="field-btn">
              <button type="submit" class="submit">Log in</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { User } from '@/models/user'
import { mapState } from 'vuex'

export default {
  name: 'login.vue',
  inject: ['sessionSBService'],
  computed: {
    ...mapState(['loggedIn', 'loggedInAsAdmin'])
  },
  data () {
    return {
      email: '',
      password: '',
      signOut: this.$route.query.signOut
    }
  },
  methods: {
    async login () {
      try {
        const user = await this.sessionSBService.asyncSignIn({
          mail: this.email,
          password: this.password
        })
        if (user !== null) {
          const userType = user.body.userType
          window.localStorage.setItem('profileId', user.body.id)

          if (userType === User.userTypes.Admin) {
            this.$store.commit('setLoggedInAsAdmin', true)
            this.$store.commit('setSuccess', true)
            this.$store.commit('setSuccessMessage', 'Logged in!')
            setTimeout(() => {
              this.$store.commit('setSuccess', false)
              this.$store.commit('setSuccessMessage', null)
            }, 8000)
            this.$router.push({ path: '/admin' })
            this.$forceUpdate()
          } else if (userType === User.userTypes.SuperUser) {
            this.$store.commit('setLoggedInAsSuperUser', true)
            this.$store.commit('setSuccess', true)
            this.$store.commit('setSuccessMessage', 'Logged in!')
            setTimeout(() => {
              this.$store.commit('setSuccess', false)
              this.$store.commit('setSuccessMessage', null)
            }, 8000)
            this.$router.push({ path: '/admin' })
            this.$forceUpdate()
          } else {
            this.$store.commit('setLoggedIn', true)
            this.$store.commit('setSuccess', true)
            this.$store.commit('setSuccessMessage', 'Logged in!')
            setTimeout(() => {
              this.$store.commit('setSuccess', false)
              this.$store.commit('setSuccessMessage', null)
            }, 8000)
            this.$router.push({ path: '/home' })
            this.$forceUpdate()
          }
        }
      } catch (e) {
        console.error(e)
        this.$store.commit('setError', true)
        this.$store.commit('setErrorMessage', 'Login failed with email : ' + this.email)
        setTimeout(() => {
          this.$store.commit('setError', false)
          this.$store.commit('setErrorMessage', null)
        }, 8000)
      }
    }
  },
  created () {
    if (this.signOut) {
      this.$store.commit('setLoggedInAsAdmin', false)
      this.$store.commit('setLoggedIn', false)
    }
  }
}
</script>

<style scoped>
</style>
