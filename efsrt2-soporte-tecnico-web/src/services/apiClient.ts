async function request<T>(
    url: string,
    options: RequestInit = {}
): Promise<T> {

    const response = await fetch(url, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...options.headers
        }
    });

    if (!response.ok) {

        const error = await response
            .json()
            .catch(() => null);

        throw new Error(
            error?.mensaje ??
            `Error HTTP ${response.status}`
        );
    }

    if (response.status === 204) {
        return undefined as T;
    }

    return response.json();
}

export const apiClient = {

    get<T>(url: string): Promise<T> {
        return request<T>(url);
    },

    post<T>(
        url: string,
        body: unknown
    ): Promise<T> {

        return request<T>(url, {
            method: "POST",
            body: JSON.stringify(body)
        });
    },

    put<T>(
        url: string,
        body: unknown
    ): Promise<T> {

        return request<T>(url, {
            method: "PUT",
            body: JSON.stringify(body)
        });
    }

};