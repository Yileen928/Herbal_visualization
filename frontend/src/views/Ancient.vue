<template>
  <div class="Ancient">
    <div class="book-wrapper">
      <div id="flipbook">
        <div class="hard double"><img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/首页.png" alt="封面首页" /></div>
                <!-- 兰茂视频页 -->
        <div class="page">
          <video ref="lanmaoVideo"src="/videos/lanmao.mp4"class="page-video" controls preload="auto">
          ></video>
        </div>
        <div class="page"><img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/介绍.png" alt="介绍图"/></div>
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/三七.png" class="page-img" alt="三七图" @click="goToHerbal('三七')" />
          </div>
        </div>
        <!-- 三七3D模型页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <ThreeModel modelPath="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/sanqi.fbx" modelName="三七" />
          </div>
        </div>
        <!-- 滇重楼图片页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/滇重楼.png" class="page-img" alt="滇重楼图" @click="goToHerbal('滇重楼')" />
          </div>
        </div>
        <!-- 滇重楼3D模型页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <ThreeModel modelPath="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/dianchonglou.fbx" modelName="滇重楼" />
          </div>
        </div>
        <!-- 天麻图片页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/天麻.png" class="page-img" alt="天麻图" @click="goToHerbal('天麻')" />
          </div>
        </div>
        <!-- 天麻3D模型页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <ThreeModel modelPath="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/tianma.fbx" modelName="天麻" />
          </div>
        </div>        
        <!-- 砂仁图片页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/砂仁.png" class="page-img" alt="砂仁图" @click="goToHerbal('砂仁')" />
          </div>
        </div>
        <!-- 砂仁3D模型页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <ThreeModel modelPath="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/sharen.fbx" modelName="砂仁" />
          </div>
        </div>
        <!-- 灯盏花图片页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/灯盏花.png" class="page-img" alt="灯盏花图" @click="goToHerbal('灯盏花')" />
          </div>
        </div>
        <!-- 灯盏花3D模型页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <ThreeModel modelPath="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/dengzhanhua.fbx" modelName="灯盏花" />
          </div>
        </div>
        <!-- 滇龙胆图片页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/滇龙胆草.png" class="page-img" alt="滇龙胆草图" @click="goToHerbal('滇龙胆')" />
          </div>
        </div>
        <!-- 滇龙胆3D模型页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <ThreeModel modelPath="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/dianlongdan.fbx" modelName="滇龙胆" />
          </div>
        </div>        
        <!-- 云木香图片页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/云木香.png" class="page-img" alt="云木香图" @click="goToHerbal('云木香')" />
          </div>
        </div>
        <!-- 云木香3D模型页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <ThreeModel modelPath="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/yunmuxiang.fbx" modelName="云木香" />
          </div>
        </div>  
        <!-- 云茯苓图片页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/云茯苓.png" class="page-img" alt="云茯苓图" @click="goToHerbal('云茯苓')" />
          </div>
        </div>
        <!-- 云茯苓3D模型页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <ThreeModel modelPath="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/云茯苓.fbx" modelName="云茯苓" />
          </div>
        </div> 
        <!-- 云当归图片页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/云当归.png" class="page-img" alt="云当归图" @click="goToHerbal('云当归')" />
          </div>
        </div>
        <!-- 云当归3D模型页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <ThreeModel modelPath="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/云当归.fbx" modelName="云当归" />
          </div>
        </div>             
        <!-- 铁皮石斛图片页 -->
          <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <img src="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/铁皮石斛.png" class="page-img" alt="铁皮石斛图" @click="goToHerbal('铁皮石斛')" />
          </div>
        </div>
        <!-- 铁皮石斛3D模型页 -->
        <div class="page">
          <div class="page-bg" style="background-image: url('https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/images/背景.png');">
            <ThreeModel modelPath="https://csx-1333791515.cos.ap-chengdu.myqcloud.com/herbs_pc_jt/铁皮石斛.fbx" modelName="铁皮石斛" />
          </div>
        </div>       
        </div>        
       <!-- 进度条组件 -->
      <div class="progress-container">
        <input
        type="range"
        min="1"
        :max="totalPages"
        v-model="currentPage"
        @input="handleSliderChange"
        class="progress-slider"
      />

        <span class="page-counter">{{ currentPage }} / {{ totalPages }}</span>
      </div>
    </div>
    </div>
</template>

<script setup>
import { onBeforeMount, ref } from 'vue'
import { useRouter } from 'vue-router'
import ThreeModel from '../components/ThreeModel.vue'

const router = useRouter()
const currentPage = ref(1)
const totalPages = 22
let flipbookInstance = null

const goToHerbal = (herbName) => {
  router.push({ path: '/herbal', query: { herbName } })
}

// 处理滑块实时输入
const handleSliderInput = (event) => {
  currentPage.value = parseInt(event.target.value)
}

const handleSliderChange = () => {
  if (flipbookInstance && flipbookInstance.data('turn')) {
    flipbookInstance.turn('page', currentPage.value)
  }
}


onBeforeMount(() => {
  const script = document.createElement('script')
  script.src = '/lib/turn.js'
  script.onload = () => {
    document.getElementById('flipbook')?.classList.add('turnjs-ready')
    if (typeof $.fn.turn === 'undefined') {
      console.error('turn.js still not available')
      return
    }

    flipbookInstance = $('#flipbook')
    flipbookInstance.turn({
      width: 1200,
      height: 800,
      autoCenter: true,
      display: 'double',
      elevation: 50,
      gradients: true,
      when: {
        turning: function (event, page) {
          currentPage.value = page
        }
      }
    })

    flipbookInstance.turn('page', currentPage.value)
  }

  script.onerror = () => {
    console.error('Error loading turn.js')
  }

  document.head.appendChild(script)
})
</script>

<style scoped>
@import './ancient.css';
</style>
