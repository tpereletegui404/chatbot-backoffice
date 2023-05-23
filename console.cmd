@echo off
@IF NOT EXIST "tools\console\node_modules" (
    call yarn --cwd tools\console install
)

call yarn --cwd tools\console start %*
