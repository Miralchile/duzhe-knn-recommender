<template>
	<view class="title">
		<block v-for="(item,index) in title" :key="index" >
			<text :class="{activetext:index == num}" @click="menubtn(index,item.name)">{{item}}</text>
		</block>
	</view>
</template>

<script>

	export default {
		
		data() {
			return {
				num:0,
				title:['好友','动态','发现']
			}
		},
		methods: {
		     menubtn(index){
				 this.num = index
				 //子组件调用父组件方法：parent
				 this.$parent.fatherMethod(index)
			 }
		}
	}
</script>

<style scoped> 
	  
	  .title{
		  display: flex;
		  padding: 10 15upx;
		  justify-content: space-between;
		  align-items: center;
	  }
	  .title text{
		  /* margin-right: 60upx;
		  display: block; */
		  display: flex;
		  height: 80upx;
		  line-height: 90upx;
		  font-size: 40upx;
		  color: #666666;
	  }
	  .activetext{
		  color: #333333 !important;
		  font-weight: bold !important;
		  border-bottom: 5upx solid #5582ff ;
	  }
</style>
