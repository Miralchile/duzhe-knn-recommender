<template>
  <view>
	  <!-- <cu-custom class="navBox" bgColor="bg-gradual-blue" :isBack="true">
	  	<block slot="content">在线选座</block>
	  </cu-custom>
	  -->
    <anil-seat 
      title="读者小站欢迎您"
      room-name="兰州站"
      :seat-data="seatData" 
      :max="4" 
      @confirm="onConfirmSeat">
    </anil-seat>
  </view>
</template>

<script>
  import anilSeat from '@/pages/social/components/find/anil-seat.vue'
  import {
  	seatData
  } from '@/common/social/seat-data.js'
  export default {
	components: {anilSeat},
    data() {
      return {
        seatData: seatData,
      };
    },
    methods: {
      onConfirmSeat () {
		  // console.log(items)
		  uni.navigateTo({
		  	url:'seatsuccess'
		  })
      }
    }
  }
</script>

<style lang="scss">
page {
  background-color: #eee;
  height: 100%;
}
</style>
