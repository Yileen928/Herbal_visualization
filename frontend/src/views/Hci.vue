<template>
  <div class="background-container">
    <button  v-if="!connected" @click="connectAndPlay">
      <img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/acientbutton.png" alt="Hcibutton" class="Hcibutton">
    </button>
    <div v-if="videoUrl" v-html="videoHtml"></div>
  </div>
</template>

<script>
export default {
  name: "HerbVideo",
  data() {
    return {
      connected: false,
      videoUrl: "",
      videoHtml: "",
      socket: null,
    };
  },
  methods: {
    connectAndPlay() {
      this.socket = new WebSocket("ws://121.4.116.71:10010/match");

      this.socket.onopen = () => {
        this.connected = true;
        this.socket.send("choose_client");
      };

      this.socket.onmessage = (event) => {
        try {
          const data = JSON.parse(event.data);
          if (data.action === "herb_selected" && data.herbName) {
            const encodedName = encodeURIComponent(data.herbName);
            const videoUrl = `https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_lz/${encodedName}.mp4`;
            this.videoUrl = videoUrl;
            this.videoHtml = `
              <video width="100%" height="100%" autoplay muted>
                <source src="${videoUrl}" type="video/mp4">
              </video>
            `;
          }
        } catch {
          // 忽略无效消息
        }
      };
    }
  }
};
</script>

<style scoped>
@import './Hci.css';
</style>
