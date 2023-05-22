import '@testing-library/jest-dom/extend-expect'
import 'asimov-javascript-extensions'
import './test/core/common/base/testing/jestExtensions'
import * as matchers from 'jest-extended'

expect.extend(matchers)

jest.mock('next/router', () => require('next-router-mock'))
jest.mock('next/dist/client/router', () => require('next-router-mock'))
