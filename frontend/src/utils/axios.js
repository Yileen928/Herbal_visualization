import axios from 'axios'

const instance = axios.create({
  baseURL: '/api',
  timeout: 5000
})

instance.interceptors.response.use(
  response => response,
  error => {
    console.error('API请求失败:', error)
    return Promise.reject(error)
  }
)

export default instance