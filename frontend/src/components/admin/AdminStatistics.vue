<template>
  <div class="container-admin">
     <div class="breadcrum-admin breadcrum-admin-margin">
      <router-link :to="{ name: 'admin' }">Admin</router-link>
      <p>></p>
      <router-link :to="{ name: 'statistics' }">Statistics</router-link>
    </div>
    <div class="users-amount-container">
      <div class="legend">
        <p class="total">Total Users: {{ totalUsers }}</p>
        <p class="entrepreneur">Entrepreneurs: {{ entrepreneurCount }}</p>
        <p class="partner">Partners: {{ partnerCount }}</p>
        <p class="admin">Admins: {{ adminCount }}</p>
        <p class="superUser">SuperUsers: {{ superuserCount }}</p>
      </div>
      <div class="canvas-container">
        <canvas class="block-chart" id="chart"></canvas>
      </div>
    </div>
  </div>
</template>

<script>
import Chart from 'chart.js/auto'
import { User } from '@/models/user'
import ErrorPopUp from '@/components/errorPopUp.vue' // Import Chart.js

export default {
  name: 'AdminStatistics',
  inject: ['usersServiceAdmin'],
  data () {
    return {
      users: [],
      totalUsers: 0,
      entrepreneurCount: 0,
      partnerCount: 0,
      adminCount: 0,
      superuserCount: 0
    }
  },
  methods: {
    /**
     * Initialize the users stats
     */
    loadDataAndCreateChart () {
      this.totalUsers = this.users.length
      this.entrepreneurCount = this.users.filter(user => user.userType === User.userTypes.Entrepreneur).length
      this.partnerCount = this.users.filter(user => user.userType === User.userTypes.Partner).length
      this.adminCount = this.users.filter(user => user.userType === User.userTypes.Admin).length
      this.superuserCount = this.users.filter(user => user.userType === User.userTypes.SuperUser).length

      this.createBarChart()
    },
    /**
     * Initialize BarChart with the users data
     */
    createBarChart () {
      const chart = document.getElementById('chart').getContext('2d')
      // eslint-disable-next-line no-new
      new Chart(chart, {
        type: 'bar',
        data: {
          labels: ['Entrepreneurs', 'Partners', 'Admins', 'SuperUsers'],
          datasets: [{
            data: [this.entrepreneurCount, this.partnerCount, this.adminCount, this.superuserCount],
            backgroundColor: ['blue', 'green', 'orange', 'red']
          }]
        },
        options: {
          indexAxis: 'y',
          scales: {
            x: {
              beginAtZero: true
            },
            y: {
              beginAtZero: true
            }
          },
          legend: {
            display: false
          }
        }
      })
    }
  },
  watch: {
    'users' () {
      this.loadDataAndCreateChart()
    }
  },
  async created () {
    try {
      this.users = await this.usersServiceAdmin.asyncFindAll()
    } catch (e) {
      console.error(e.toJSON())
    }
  }
}
</script>

<style scoped>

.users-amount-container {
  height: 80%;
  width: 90%;
  margin-top: 30px;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
</style>
