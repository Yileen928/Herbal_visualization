// src/utils/readExcel.ts
import readXlsxFile from 'read-excel-file'

export const readExcelContent = async (columnName: string): Promise<string[]> => {
  try {
    // 直接从public/data读取
    const response = await fetch('Herbal_visualization/frontend/public/data/云南白药膏药.xlsx')
    const blob = await response.blob()

    const { rows } = await readXlsxFile(blob, {
      schema: {
        [columnName]: {
          prop: columnName,
          type: String
        }
      }
    })

    return rows.map(row => row[columnName] as string).filter(Boolean)
  } catch (error) {
    console.error('读取Excel失败:', error)
    return []
  }
}