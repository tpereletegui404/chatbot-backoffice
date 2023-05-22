const nextJest = require('next/jest')

const createJestConfig = nextJest({ dir: './' })

const customJestConfig = {
  setupFilesAfterEnv: ['<rootDir>/jest.setup.js'],
  testRegex: '(/test/(.*)\\.(test|spec))\\.(ts|js|tsx|jsx)$',
  moduleNameMapper: {
    '^@/(.*)$': '<rootDir>/src/$1',
    '^/public/(.*)$': '<rootDir>/public/$1',
  },
  testEnvironment: 'jest-environment-jsdom',
}

module.exports = createJestConfig(customJestConfig)
