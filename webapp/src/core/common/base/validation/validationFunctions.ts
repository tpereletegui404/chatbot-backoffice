export function isNullOrBlank(value) {
    const strValue = value?.toString().trim()
    return value === null || strValue?.length === 0
}
