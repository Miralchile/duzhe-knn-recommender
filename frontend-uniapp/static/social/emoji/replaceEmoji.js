import emoji_List from './emojiList.js';
export default{
	replaceEmoji(content) {
		let replacedStr = content.replace(/\[([^(\]|\[)]*)\]/g, (item, index) => {
			for (let i = 0; i < emoji_List.length; i++) {
				let EM = emoji_List[i];
				if (EM.alt == item) {
					let Path = '/static/social/emoji/big/';
					let imgstr = '<img class=\'emoji-text\' src="' + Path + EM.url + '">';
					return imgstr;
				}
			}
			return item;
		});
		return '<div class=\'text-show\'>' + replacedStr + '</div>';
	},
}