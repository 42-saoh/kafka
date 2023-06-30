import axios, { AxiosRequestConfig } from 'axios' // 추가
import { CustomAxiosInterface } from './axiosInterface'
import { CommonResponse } from './customResponse'

const client: CustomAxiosInterface = axios.create({
    baseURL: 'http://localhost:3000/api',
})

export const Get = async <T>(
  url: string,
  config?: AxiosRequestConfig
): Promise<CommonResponse<T>> => {
  const response = await client.get<CommonResponse<T>>(url, config)
  return response
}

export const Put = async <T>(
  url: string,
  data?: T,
  config?: AxiosRequestConfig
): Promise<CommonResponse<T>> => {
  const response = await client.put<CommonResponse<T>>(url, data, config)
  return response
}
