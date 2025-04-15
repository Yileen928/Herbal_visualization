import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';
import { dirname } from 'path';

// 获取当前文件的目录
const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

// 动态导入 JSON 文件
const rawDataPath = path.join(__dirname, '../assets/yunnan_new.json');
const rawData = JSON.parse(fs.readFileSync(rawDataPath, 'utf8'));

// 提取云南省数据
const yunnanData = rawData.features.find(
  feature => feature.properties.NAME_1 === 'Yunnan'
);

if (!yunnanData) {
  console.error('未找到云南省数据，请检查数据源');
  process.exit(1);
}

// 转换为 ECharts 格式
const processedData = {
  type: 'FeatureCollection',
  features: [{
    type: 'Feature',
    properties: {
      name: 'Yunnan',
      cp: [101.5, 25.5],  // 中心点
      childNum: 1
    },
    geometry: yunnanData.geometry,
    elevation: yunnanData.properties.ELEVATION || 0  // 添加高程数据
  }]
};

// 保存处理后的数据
const outputPath = path.join(__dirname, '../assets/yunnan_processed.json');
fs.writeFileSync(outputPath, JSON.stringify(processedData, null, 2));

console.log('数据处理完成，已保存到:', outputPath);