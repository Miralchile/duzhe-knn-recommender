<template>
	<view>
		<view>
			<view style="
            position: fixed;
            z-index: 1;
            bottom: 40px;
            right: 0;">
			<image @click="testlist" src="/static/social/location.png" style="width: 40px;height: 40px;"></image>
			
				<image @click="test" src="/static/social/location.png" style="width: 40px;height: 40px;"></image>
				<image @click="location" src="/static/social/location.png" style="width: 40px;height: 40px;"></image>
			</view>
		</view>
		<map id="map" ref="map" :style="'width: ' + width + '; height: ' + height + ';'" :scale="map.scale" @regionchange="regionchange"
		 @updated="updated" :latitude="map.latitude" :longitude="map.longitude" :markers="covers" @callouttap="de" @markertap="de">


		</map>
	</view>
</template>

<script>
	// import zjMarker()
	var map;
	export default {
		data() {
			return {
				height: '500px',
				width: '500px',
				map: {
					scale: 13,
					latitude: 39.909,
					longitude: 116.39742
				},
				covers: [{
					latitude: 39.909,
					longitude: 116.39742
				}],
				list: [{
					"id": 1264640,
					"user_id": 335187,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 1,
					"reviews": 0,
					"shares": 0,
					"text": "2020年只剩2个月了，无论你今年经历了什么，那都已成过往，希望今年的遗憾，都是明年惊喜的铺垫，加油加油！",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53616924,
					"latitude": "27.267520",
					"longitude": "111.727120",
					"createtime": 1604206554,
					"updatetime": 1604208624,
					"deletetime": null,
					"status": "normal",
					"nickName": "Gentlema",
					"avatar": "https://image.aishencn.com/2020/10/20/002207441_40845724-user.jpg",
					"bio": "",
					"distance": 13419,
					"images_arr": [""]
				}, {
					"id": 1272720,
					"user_id": 36950,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 2,
					"reviews": 0,
					"shares": 0,
					"text": "耶斯 莫拉",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53626331,
					"latitude": "27.257640",
					"longitude": "111.747910",
					"createtime": 1604251112,
					"updatetime": 1604251277,
					"deletetime": null,
					"status": "normal",
					"nickName": "妖仙.",
					"avatar": "https://image.aishencn.com/2020/04/09/004135379_37869100-user.jpg",
					"bio": "",
					"distance": 27070,
					"images_arr": [""]
				}, {
					"id": 1316740,
					"user_id": 366172,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 1,
					"reviews": 0,
					"shares": 0,
					"text": "生活不易丶且行且珍惜",
					"images": "https://image.aishencn.com/2020/11/04/215134979_98197351-bbs.jpg",
					"weigh": 0,
					"collect": 1,
					"third_id": 53655712,
					"latitude": "27.257430",
					"longitude": "111.732960",
					"createtime": 1604497896,
					"updatetime": 1604502723,
					"deletetime": null,
					"status": "normal",
					"nickName": "过往",
					"avatar": "https://image.aishencn.com/2020/11/04/182622730_98197351-user.venus/cache/ext/crop/1604518314542_jpg",
					"bio": "",
					"distance": 27190,
					"images_arr": ["https://image.aishencn.com/2020/11/04/215134979_98197351-bbs.jpg"]
				}, {
					"id": 1322662,
					"user_id": 368782,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 5,
					"reviews": 0,
					"shares": 0,
					"text": "“没有遇见你之前，我觉得人生亦不过如此",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53659496,
					"latitude": "27.271350",
					"longitude": "111.804710",
					"createtime": 1604525804,
					"updatetime": 1604536039,
					"deletetime": null,
					"status": "normal",
					"nickName": "救赎.",
					"avatar": "https://image.aishencn.com/2020/11/04/220729994_98895418-user.jpg",
					"bio": "",
					"distance": 27950,
					"images_arr": [""]
				}, {
					"id": 1323519,
					"user_id": 102623,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 4,
					"reviews": 0,
					"shares": 0,
					"text": "姑凉阿  擦干眼泪\n爱你的人不会让你满面泪水\n他在别人的怀里沉醉\n你又何必独自角落伤悲",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53660361,
					"latitude": "27.278180",
					"longitude": "111.735640",
					"createtime": 1604540131,
					"updatetime": 1604541088,
					"deletetime": null,
					"status": "normal",
					"nickName": "TZ",
					"avatar": "https://image.aishencn.com/2020/04/13/113855467_63398803-user.jpg",
					"bio": "",
					"distance": 29174,
					"images_arr": [""]
				}, {
					"id": 1330305,
					"user_id": 368782,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 7,
					"reviews": 0,
					"shares": 0,
					"text": "公主在城堡下淋了一晚上的雨 王子也没有来 但骑士一直都在 我的意思是你可以寻找那个更值得让你奔赴的那个人",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53664210,
					"latitude": "27.271440",
					"longitude": "111.804850",
					"createtime": 1604573707,
					"updatetime": 1604581746,
					"deletetime": null,
					"status": "normal",
					"nickName": "救赎.",
					"avatar": "https://image.aishencn.com/2020/11/04/220729994_98895418-user.jpg",
					"bio": "",
					"distance": 30799,
					"images_arr": [""]
				}, {
					"id": 1330481,
					"user_id": 366172,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 0,
					"reviews": 0,
					"shares": 0,
					"text": "一直在路上，不为旅行，只为在未知的途中遇见更好的我们",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53664285,
					"latitude": "27.280920",
					"longitude": "111.737570",
					"createtime": 1604574204,
					"updatetime": 1604582480,
					"deletetime": null,
					"status": "normal",
					"nickName": "过往",
					"avatar": "https://image.aishencn.com/2020/11/04/182622730_98197351-user.venus/cache/ext/crop/1604518314542_jpg",
					"bio": "",
					"distance": 63838,
					"images_arr": [""]
				}, {
					"id": 1330710,
					"user_id": 372568,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 3,
					"reviews": 0,
					"shares": 0,
					"text": "从此我的世界将变得模糊",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53663782,
					"latitude": "27.254010",
					"longitude": "111.747450",
					"createtime": 1604570320,
					"updatetime": 1604583739,
					"deletetime": null,
					"status": "normal",
					"nickName": "星星自述曲",
					"avatar": "https://image.aishencn.com/2020/10/30/053708194_91142779-user.jpg",
					"bio": "",
					"distance": 82756,
					"images_arr": [""]
				}, {
					"id": 1331608,
					"user_id": 368782,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 3,
					"reviews": 0,
					"shares": 0,
					"text": "“希望以后喜欢的人，不要让我哭，不要让我受委屈，不要不理我，更不要放弃我＂",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53665113,
					"latitude": "27.271460",
					"longitude": "111.804690",
					"createtime": 1604579672,
					"updatetime": 1604588770,
					"deletetime": null,
					"status": "normal",
					"nickName": "救赎.",
					"avatar": "https://image.aishencn.com/2020/11/04/220729994_98895418-user.jpg",
					"bio": "",
					"distance": 87636,
					"images_arr": [""]
				}, {
					"id": 1335017,
					"user_id": 374069,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 1,
					"reviews": 0,
					"shares": 0,
					"text": "随机抽取送了吧\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n送我",
					"images": "https://image.aishencn.com/2020/11/06/015538056_36084241-bbs.jpg",
					"weigh": 0,
					"collect": 1,
					"third_id": 53669093,
					"latitude": "27.264500",
					"longitude": "111.753810",
					"createtime": 1604598940,
					"updatetime": 1604608038,
					"deletetime": null,
					"status": "normal",
					"nickName": "东东",
					"avatar": "https://image.aishencn.com/2020/02/27/121802570_36084241-user.jpg",
					"bio": "",
					"distance": 97973,
					"images_arr": ["https://image.aishencn.com/2020/11/06/015538056_36084241-bbs.jpg"]
				}, {
					"id": 1336305,
					"user_id": 368782,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 0,
					"reviews": 0,
					"shares": 0,
					"text": "我只想凤冠霞帔，嫁意中人，惟愿执子之手，与子偕老。琴瑟在御，莫不静好。这是我向往的...",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53669841,
					"latitude": "27.271410",
					"longitude": "111.804750",
					"createtime": 1604613789,
					"updatetime": 1604615952,
					"deletetime": null,
					"status": "normal",
					"nickName": "救赎.",
					"avatar": "https://image.aishencn.com/2020/11/04/220729994_98895418-user.jpg",
					"bio": "",
					"distance": 133324,
					"images_arr": [""]
				}, {
					"id": 1353385,
					"user_id": 368782,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 0,
					"reviews": 0,
					"shares": 0,
					"text": "二选一的时候记得放弃我 因为我不想成为别人的第二选择 单选的时候也要记得放弃我 我不想成为你迫不得已的选项 我只想成为你的偏爱和例外",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53682432,
					"latitude": "27.271610",
					"longitude": "111.804870",
					"createtime": 1604710321,
					"updatetime": 1604712076,
					"deletetime": null,
					"status": "normal",
					"nickName": "救赎.",
					"avatar": "https://image.aishencn.com/2020/11/04/220729994_98895418-user.jpg",
					"bio": "",
					"distance": 138400,
					"images_arr": [""]
				}, {
					"id": 1373304,
					"user_id": 274507,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 8,
					"reviews": 0,
					"shares": 0,
					"text": "祝我们好在秋天",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53700465,
					"latitude": "27.234460",
					"longitude": "111.790840",
					"createtime": 1604816918,
					"updatetime": 1604822237,
					"deletetime": null,
					"status": "normal",
					"nickName": "萌",
					"avatar": "https://image.aishencn.com/2020/05/07/100612550_58573312-user.jpg",
					"bio": "",
					"distance": 140463,
					"images_arr": [""]
				}, {
					"id": 1391504,
					"user_id": 368782,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 0,
					"reviews": 0,
					"shares": 0,
					"text": "女孩子嘛，你越懂她，她越离不开你，你对她好，她心里明白，哪怕你和她住出租屋吃泡面她也心甘情愿。",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53714785,
					"latitude": "27.271390",
					"longitude": "111.804810",
					"createtime": 1604918966,
					"updatetime": 1604922689,
					"deletetime": null,
					"status": "normal",
					"nickName": "救赎.",
					"avatar": "https://image.aishencn.com/2020/11/04/220729994_98895418-user.jpg",
					"bio": "",
					"distance": 142439,
					"images_arr": [""]
				}, {
					"id": 1398488,
					"user_id": 368782,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 0,
					"reviews": 0,
					"shares": 0,
					"text": "真诚  温柔  耐心  陪伴，是我最喜欢的四个词",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53720931,
					"latitude": "27.271100",
					"longitude": "111.804780",
					"createtime": 1604960500,
					"updatetime": 1604960841,
					"deletetime": null,
					"status": "normal",
					"nickName": "救赎.",
					"avatar": "https://image.aishencn.com/2020/11/04/220729994_98895418-user.jpg",
					"bio": "",
					"distance": 153462,
					"images_arr": [""]
				}, {
					"id": 1406721,
					"user_id": 402917,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 2,
					"reviews": 0,
					"shares": 0,
					"text": "我想过夏天",
					"images": "https://image.aishencn.com/2020/11/10/193212863_98858587-bbs.jpg",
					"weigh": 0,
					"collect": 1,
					"third_id": 53726111,
					"latitude": "27.232600",
					"longitude": "111.796070",
					"createtime": 1605007935,
					"updatetime": 1605008899,
					"deletetime": null,
					"status": "normal",
					"nickName": "點點",
					"avatar": "https://image.aishencn.com/2020/11/10/184249621_98858587-user.venus/cache/ext/crop/1605037921877_jpg",
					"bio": "",
					"distance": 324077,
					"images_arr": ["https://image.aishencn.com/2020/11/10/193212863_98858587-bbs.jpg"]
				}, {
					"id": 1407950,
					"user_id": 372568,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 9,
					"reviews": 0,
					"shares": 0,
					"text": "🍎很甜哦！",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53725499,
					"latitude": "27.254530",
					"longitude": "111.747870",
					"createtime": 1605003930,
					"updatetime": 1605015918,
					"deletetime": null,
					"status": "normal",
					"nickName": "星星自述曲",
					"avatar": "https://image.aishencn.com/2020/10/30/053708194_91142779-user.jpg",
					"bio": "",
					"distance": 359099,
					"images_arr": [""]
				}, {
					"id": 1408491,
					"user_id": 402917,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 6,
					"reviews": 0,
					"shares": 0,
					"text": "晚安啦  各位亲",
					"images": "https://image.aishencn.com/2020/11/10/211754256_98858587-bbs.jpg",
					"weigh": 0,
					"collect": 1,
					"third_id": 53727321,
					"latitude": "27.231580",
					"longitude": "111.797950",
					"createtime": 1605014278,
					"updatetime": 1605018791,
					"deletetime": null,
					"status": "normal",
					"nickName": "點點",
					"avatar": "https://image.aishencn.com/2020/11/10/184249621_98858587-user.venus/cache/ext/crop/1605037921877_jpg",
					"bio": "",
					"distance": 372723,
					"images_arr": ["https://image.aishencn.com/2020/11/10/211754256_98858587-bbs.jpg"]
				}, {
					"id": 1413734,
					"user_id": 402917,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 0,
					"reviews": 0,
					"shares": 0,
					"text": "美好的一天开始啦。",
					"images": "https://image.aishencn.com/2020/11/11/062138234_98858587-bbs.jpg",
					"weigh": 0,
					"collect": 1,
					"third_id": 53731777,
					"latitude": "27.231570",
					"longitude": "111.797990",
					"createtime": 1605046900,
					"updatetime": 1605047222,
					"deletetime": null,
					"status": "normal",
					"nickName": "點點",
					"avatar": "https://image.aishencn.com/2020/11/10/184249621_98858587-user.venus/cache/ext/crop/1605037921877_jpg",
					"bio": "",
					"distance": 372865,
					"images_arr": ["https://image.aishencn.com/2020/11/11/062138234_98858587-bbs.jpg"]
				}, {
					"id": 1424429,
					"user_id": 402917,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳市",
					"views": 0,
					"likes": 4,
					"reviews": 0,
					"shares": 0,
					"text": "喜欢夏天的女孩👧🏿",
					"images": "https://image.aishencn.com/2020/11/11/195747214_98858587-bbs.jpg",
					"weigh": 0,
					"collect": 1,
					"third_id": 53737407,
					"latitude": "27.231570",
					"longitude": "111.797970",
					"createtime": 1605095869,
					"updatetime": 1605107903,
					"deletetime": null,
					"status": "normal",
					"nickName": "點點",
					"avatar": "https://image.aishencn.com/2020/11/10/184249621_98858587-user.venus/cache/ext/crop/1605037921877_jpg",
					"bio": "",
					"distance": 372978,
					"images_arr": ["https://image.aishencn.com/2020/11/11/195747214_98858587-bbs.jpg"]
				}, {
					"id": 1449019,
					"user_id": 416790,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳",
					"views": 0,
					"likes": 3,
					"reviews": 0,
					"shares": 0,
					"text": "没人喜欢呗",
					"images": "https://image.aishencn.com/2020/11/13/132837448_77242729-bbs.venus/cache/-1636427911/pub/202011/1605245317221_jpg",
					"weigh": 0,
					"collect": 1,
					"third_id": 53756433,
					"latitude": "27.324050",
					"longitude": "111.767780",
					"createtime": 1605245318,
					"updatetime": 1605246666,
					"deletetime": null,
					"status": "normal",
					"nickName": "多多益善",
					"avatar": "https://image.aishencn.com/2020/02/27/183118049_77242729-user.jpg",
					"bio": "",
					"distance": 383642,
					"images_arr": [
						"https://image.aishencn.com/2020/11/13/132837448_77242729-bbs.venus/cache/-1636427911/pub/202011/1605245317221_jpg"
					]
				}, {
					"id": 1452015,
					"user_id": 416790,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳",
					"views": 0,
					"likes": 2,
					"reviews": 0,
					"shares": 0,
					"text": "烦人烦人烦人",
					"images": "https://image.aishencn.com/2020/11/13/181943382_77242729-bbs.jpg",
					"weigh": 0,
					"collect": 1,
					"third_id": 53758847,
					"latitude": "27.280350",
					"longitude": "111.744920",
					"createtime": 1605262784,
					"updatetime": 1605263590,
					"deletetime": null,
					"status": "normal",
					"nickName": "多多益善",
					"avatar": "https://image.aishencn.com/2020/02/27/183118049_77242729-user.jpg",
					"bio": "",
					"distance": 383670,
					"images_arr": ["https://image.aishencn.com/2020/11/13/181943382_77242729-bbs.jpg"]
				}, {
					"id": 1465247,
					"user_id": 372568,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳",
					"views": 0,
					"likes": 2,
					"reviews": 0,
					"shares": 0,
					"text": "我只是变成我原来的样子了\n变安静了、不爱笑了、不说话了\n回到了自己最初的样子…",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53769377,
					"latitude": "27.254650",
					"longitude": "111.748050",
					"createtime": 1605328818,
					"updatetime": 1605336492,
					"deletetime": null,
					"status": "normal",
					"nickName": "星星自述曲",
					"avatar": "https://image.aishencn.com/2020/10/30/053708194_91142779-user.jpg",
					"bio": "",
					"distance": 383737,
					"images_arr": [""]
				}, {
					"id": 1519276,
					"user_id": 372568,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳",
					"views": 0,
					"likes": 2,
					"reviews": 0,
					"shares": 0,
					"text": "失望攒够了…\n真的好累啊…",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53821807,
					"latitude": "27.254480",
					"longitude": "111.748030",
					"createtime": 1605676265,
					"updatetime": 1605687862,
					"deletetime": null,
					"status": "normal",
					"nickName": "星星自述曲",
					"avatar": "https://image.aishencn.com/2020/10/30/053708194_91142779-user.jpg",
					"bio": "",
					"distance": 383974,
					"images_arr": [""]
				}, {
					"id": 1593483,
					"user_id": 102623,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳",
					"views": 0,
					"likes": 1,
					"reviews": 0,
					"shares": 0,
					"text": "你总是嚷嚷着要找一个对象 却从不主动勾搭 也懒得接受别人的追求 却还不肯将就 有时候也觉得单身挺好的 但又常常羡慕人家成双成  对 你到底在等谁  你到底在等谁  你到底在等谁呀   说实话我也不知道  哈哈😄",
					"images": "https://image.aishencn.com/2020/11/26/144409753_63398803-bbs.jpg",
					"weigh": 0,
					"collect": 1,
					"third_id": 53927300,
					"latitude": "27.277490",
					"longitude": "111.735690",
					"createtime": 1606373050,
					"updatetime": 1606373846,
					"deletetime": null,
					"status": "normal",
					"nickName": "TZ",
					"avatar": "https://image.aishencn.com/2020/04/13/113855467_63398803-user.jpg",
					"bio": "",
					"distance": 384319,
					"images_arr": ["https://image.aishencn.com/2020/11/26/144409753_63398803-bbs.jpg"]
				}, {
					"id": 1606350,
					"user_id": 414136,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳",
					"views": 0,
					"likes": 4,
					"reviews": 0,
					"shares": 0,
					"text": "会有人无条件爱你呀！！！",
					"images": "https://image.aishencn.com/2020/11/29/003542732_97119487-bbs.venus/cache/1626949412/pub/202011/1606581341695_jpg,https://image.aishencn.com/2020/11/29/003542742_97119487-bbs.venus/cache/1626949412/pub/202011/1606581341852_jpg",
					"weigh": 0,
					"collect": 1,
					"third_id": 53961588,
					"latitude": "27.245060",
					"longitude": "111.738140",
					"createtime": 1606581345,
					"updatetime": 1606585328,
					"deletetime": null,
					"status": "normal",
					"nickName": "散了.",
					"avatar": "https://image.aishencn.com/2020/11/07/095716548_97119487-user.jpg",
					"bio": "",
					"distance": 384637,
					"images_arr": [
						"https://image.aishencn.com/2020/11/29/003542732_97119487-bbs.venus/cache/1626949412/pub/202011/1606581341695_jpg",
						"https://image.aishencn.com/2020/11/29/003542742_97119487-bbs.venus/cache/1626949412/pub/202011/1606581341852_jpg"
					]
				}, {
					"id": 1616164,
					"user_id": 465384,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳",
					"views": 0,
					"likes": 1,
					"reviews": 0,
					"shares": 0,
					"text": "我不是高富帅",
					"images": "",
					"weigh": 0,
					"collect": 1,
					"third_id": 53985205,
					"latitude": "27.283670",
					"longitude": "111.804910",
					"createtime": 1606739639,
					"updatetime": 1606748230,
					"deletetime": null,
					"status": "normal",
					"nickName": "黄林菲",
					"avatar": "https://image.aishencn.com/2020/11/30/191536374_98323012-user.jpg",
					"bio": "",
					"distance": 384998,
					"images_arr": [""]
				}, {
					"id": 1617808,
					"user_id": 465744,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳",
					"views": 0,
					"likes": 1,
					"reviews": 0,
					"shares": 0,
					"text": "我可太开心了",
					"images": "https://image.aishencn.com/2020/12/01/050415050_82968316-bbs.jpg",
					"weigh": 0,
					"collect": 1,
					"third_id": 53990603,
					"latitude": "27.268040",
					"longitude": "111.736180",
					"createtime": 1606770254,
					"updatetime": 1606775410,
					"deletetime": null,
					"status": "normal",
					"nickName": "青叔✨",
					"avatar": "https://image.aishencn.com/2020/12/01/050631582_82968316-user.jpg",
					"bio": "",
					"distance": 409053,
					"images_arr": ["https://image.aishencn.com/2020/12/01/050415050_82968316-bbs.jpg"]
				}, {
					"id": 1627554,
					"user_id": 467952,
					"age": 0,
					"gender": 0,
					"zodiac": 0,
					"flag": "",
					"secret": "",
					"place": "邵阳",
					"views": 0,
					"likes": 1,
					"reviews": 0,
					"shares": 0,
					"text": "有妹妹要蹭标嘛",
					"images": "https://image.aishencn.com/2020/12/03/033558026_96173065-bbs.venus/cache/1539359135/pub/202012/1606937757745_jpg,https://image.aishencn.com/2020/12/03/033558036_96173065-bbs.venus/cache/1539359135/pub/202012/1606937757897_jpg",
					"weigh": 0,
					"collect": 1,
					"third_id": 54013959,
					"latitude": "27.253990",
					"longitude": "111.743780",
					"createtime": 1606937757,
					"updatetime": 1606949827,
					"deletetime": null,
					"status": "normal",
					"nickName": "墨",
					"avatar": "https://image.aishencn.com/2020/12/03/032752066_96173065-user.venus/cache/ext/crop/1606969329088_jpg",
					"bio": "",
					"distance": 441287,
					"images_arr": [
						"https://image.aishencn.com/2020/12/03/033558026_96173065-bbs.venus/cache/1539359135/pub/202012/1606937757745_jpg",
						"https://image.aishencn.com/2020/12/03/033558036_96173065-bbs.venus/cache/1539359135/pub/202012/1606937757897_jpg"
					]
				}],
				load: true
			};
		},
		onLoad() {
			// 创建地图上下文
			map = uni.createMapContext('map', this);

			uni.getSystemInfo({
				success: res => {
					this.height = res.windowHeight + 'px';
					this.width = res.windowWidth + 'px';
				}
			});

			this.location();



			// var arr = [];
			// for (var i = 0; i < this.list.length; i++) {
			// 	arr.push({
			// 		id: i + 1000,
			// 		latitude: this.list[i].latitude,
			// 		longitude: this.list[i].longitude,
			// 		iconPath: this.list[i].avatar,
			// 		width: 30,
			// 		height: 30
			// 	});
			// }

			// this.covers = arr;
		},

		methods: {

			test() {
				map.getRegion({
					success: res => {
						map.getScale({
							success: ress => {

								this.covers = this.getFortMatMarkerList(res.northeast, res.southwest, ress.scale, this.list)
							}
						})
						// "northeast": {
						// 	"longitude": 111.7890356728411,
						// 	"latitude": 27.34148740552791
						// },
						// "southwest": {
						// 	"longitude": 111.70248749493365,
						// 	"latitude": 27.17478887207441
						// },
					}
				})
			},
			//根据东北 西南经纬度 以及后台返回标记点 格式化成小程序marker点
			getFortMatMarkerList(northeast, southwest, scale, backendMarkerList) {
				//屏幕中显示的经度的长度和纬度的长度
				let mapWidth = southwest.longitude - northeast.longitude;
				let mapHeight = northeast.latitude - southwest.latitude;
				//将屏幕中地图分割的横向 格子数和 纵向格子数
				let widthSize = scale;
				let heightSize = widthSize + parseInt(scale / 2);
				//计算每个格子的经纬度的长度
				let unitWidth = mapWidth / widthSize ;
				let unitHeight = mapHeight / heightSize ;
				console.log(widthSize,heightSize)
				console.log(unitWidth,unitHeight)
				let pointData = {};
				backendMarkerList.forEach(latLng => {
					//如果点在显示范围内
					if (latLng.latitude < northeast.latitude && latLng.latitude > southwest.latitude &&
						latLng.longitude < northeast.longitude && latLng.longitude > southwest.longitude) {
						let relativeX = latLng.longitude - northeast.longitude;
						let relativeY = latLng.latitude - southwest.latitude;
						//计算出这个点，处于哪个格子
						let x = parseInt(Math.floor(relativeX / unitWidth));
						let y = parseInt(Math.floor(relativeY / unitHeight));
						if (x < 0 || y < 0) {
							console.log('点位不在格子内', '失败');
						}
						//生成单元格点位数据
						let pointKey = x + ',' + y;
						if (pointData[pointKey] == undefined) {
							pointData[pointKey] = [];
						}
							pointData[pointKey].push(latLng);
					}
				});
				console.log('pointData', pointData.length);

				let resultMapArray = [];
				for (let y = 0; y < heightSize; y++) {
					for (let x = 0; x < widthSize; x++) {
						let pointKey = x + ',' + y;
						//筛选没有充电站点位的格子
						if (pointData[pointKey] != undefined) {
							let markerItem = {};
							//聚合点与单点共存 , 长度等于一 不聚合点
							if (pointData[pointKey].length == 1) {
								// console.log('不聚合点', pointData[pointKey][0]);

								markerItem = {
									id: pointData[pointKey][0].id,
									
									latitude: pointData[pointKey][0].latitude,
									longitude: pointData[pointKey][0].longitude,
									iconPath: pointData[pointKey][0].avatar,
									width: 30,
									height: 30
								}

								// let iconPath = pointData[pointKey][0].ScanAndCharge == 1 ? '/img/scanMarkerIcon.png' : '/img/markerIcon.png';
								// markerItem = new zjMarker(pointData[pointKey][0].longitude, pointData[pointKey][0].latitude, pointData[pointKey][0].StationID, {
								//   iconPath: iconPath
								// })
								//长度大于一 聚合点
							} else if (pointData[pointKey].length > 1) {
								// console.log('聚合点');
								markerItem = {
									id: pointData[pointKey][0].id,
									
									latitude: pointData[pointKey][0].latitude,
									longitude: pointData[pointKey][0].longitude,
									iconPath: '/static/logo.png',
									width: 30,
									height: 30
								}
							}
							resultMapArray.push(markerItem);
						}
					}
				}
				console.log('resultMapArray', resultMapArray.length);
				return resultMapArray;
			},
			updated() {
				console.log('渲染完成');
			},
			// 当视野发生改变
			regionchange() {
				console.log('当视野发生改变');
				this.test();
				// let loadDelay = 200;
				// if (this.load) {
				//     console.log('等待加载');
				// } else {
				//     this.load = true;
				//     setTimeout(() => {
				//         this.getList();
				//     }, loadDelay);
				// }
			},
			//定位当前
			location() {
				console.log('开始获取地址');
				uni.getLocation({
					type: 'gcj02',
					success: res => {
						// console.log('地址获取成功,切换到目标点nvue', res);
						map.moveToLocation({
							latitude: res.latitude,
							longitude: res.longitude
						})
						// this.getList(res.latitude,res.longitude);
					},
					fail: () => {
						console.log('地理位置获取失败');
					}
				});
			},
			testlist(){
				this.getList();
			},
			getList(la = 0, lo = 0) {
				var arr = [];
				map.getCenterLocation({
					success: res => {
						let latitude = res.latitude
						let longitude = res.longitude
						if (la !== 0) {
							latitude = la
							longitude = lo
						}
						console.log('当前中心坐标', latitude + ',' + longitude);
						uni.request({
							data: {
								latitude: latitude,
								longitude: longitude
							},
							method: 'POST',
							url: 'https://api.mymoyi.cn/api/moyicosmic/test/testList',
							success: res => {
								console.log('完成请求', res);

								if (res.data.code) {
									res = res.data.data;
									for (var i = 0; i < res.list.length; i++) {
										this.list = res.list;
										// arr.push({
										// 	id: i,
										// 	callout: {
										// 		content: res.list[i].text.substr(0, 10),
										// 		color: '#000',
										// 		textAlign: 'center',
										// 		padding: 5
										// 	},
										// 	latitude: res.list[i].latitude,
										// 	longitude: res.list[i].longitude,
										// 	iconPath: res.list[i].avatar,
										// 	width: 30,
										// 	height: 30
										// });
									}
								} else {
									uni.showToast({
										title: res.data.msg,
										icon: 'none'
									});
								}
								// this.covers = arr;
								console.log('arr', arr)
							},
							complete: () => {
								this.load = false;
							}
						});
					}
				});

			},
			de(i) {
				console.log('i', i);
				let a = this.list[i.detail.markerId];
				uni.showModal({
					title: '用户：' + a.nickName,
					content: a.text
				});
			}
		}
	};
</script>

<style></style>
