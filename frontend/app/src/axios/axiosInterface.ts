import { AxiosInstance, AxiosInterceptorManager, AxiosResponse, AxiosRequestConfig, InternalAxiosRequestConfig } from "axios";

type CustomAxiosResponse<T = any> = {
    response?: T;
    resfreshToken?: string;
}

export interface CustomAxiosInterface extends AxiosInstance {
    interceptors: {
        request: AxiosInterceptorManager<InternalAxiosRequestConfig>
        response: AxiosInterceptorManager<AxiosResponse<CustomAxiosResponse>>
    }

    get<T>(url: string, config?: AxiosRequestConfig): Promise<T>;
    delete<T>(url: string, config?: AxiosRequestConfig): Promise<T>;
    post<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T>;
    put<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T>;
    patch<T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T>;
}