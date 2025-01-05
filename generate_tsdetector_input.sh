#!/bin/bash

# ------------------------------------------------------------
# Script: generate_tsdetector_input.sh
# Description: Generates tsdetector-input.csv by mapping test
#              classes to their corresponding main classes.
# Usage: ./generate_tsdetector_input.sh
# ------------------------------------------------------------

# ---------------------------
# 1. Configuration Variables
# ---------------------------

# Project Name
PROJECT_NAME="BadIceCream"

# Main and Test Directories
# Replace backslashes with forward slashes for Bash compatibility
MAIN_DIR="D:/School/Masters/1st Year/1st Semester/TVVS/Project/TVVS-Project/src/main/java/badIceCream"
TEST_DIR="D:/School/Masters/1st Year/1st Semester/TVVS/Project/TVVS-Project/src/test/java/badIceCream"

# Output CSV File Path
OUTPUT_CSV="D:/School/Masters/1st Year/1st Semester/TVVS/Project/TVVS-Project/tsdetector-input.csv"

# Temporary file to store mappings
TEMP_CSV=$(mktemp)

# ---------------------------
# 2. Function Definitions
# ---------------------------

# Function to escape commas in paths (if any)
escape_commas() {
    echo "$1" | sed 's/,/\\,/g'
}

# ---------------------------
# 3. Generate Mappings
# ---------------------------

# Ensure main and test directories exist
if [ ! -d "$MAIN_DIR" ]; then
    echo "Error: Main directory '$MAIN_DIR' does not exist."
    exit 1
fi

if [ ! -d "$TEST_DIR" ]; then
    echo "Error: Test directory '$TEST_DIR' does not exist."
    exit 1
fi

# Traverse the test directory to find all *Test.java files
find "$TEST_DIR" -type f -name "*Test.java" | while read -r TEST_FILE; do
    # Derive the relative path from TEST_DIR
    RELATIVE_PATH="${TEST_FILE#$TEST_DIR/}"

    # Replace 'Test.java' with '.java' to get the main class filename
    MAIN_FILE_RELATIVE="${RELATIVE_PATH/Test.java/.java}"

    # Construct the main class absolute path
    MAIN_FILE="$MAIN_DIR/$MAIN_FILE_RELATIVE"

    # Normalize paths (remove any double slashes)
    MAIN_FILE="$(echo "$MAIN_FILE" | sed 's|//|/|g')"
    TEST_FILE="$(echo "$TEST_FILE" | sed 's|//|/|g')"

    # Check if the main class exists
    if [ -f "$MAIN_FILE" ]; then
        # Escape any commas in paths
        ESCAPED_TEST_FILE=$(escape_commas "$TEST_FILE")
        ESCAPED_MAIN_FILE=$(escape_commas "$MAIN_FILE")

        # Append to temporary CSV without quotes
        echo "$PROJECT_NAME,$ESCAPED_TEST_FILE,$ESCAPED_MAIN_FILE" >> "$TEMP_CSV"
    else
        echo "Warning: Corresponding main class for '$TEST_FILE' not found at '$MAIN_FILE'."
    fi
done

# ---------------------------
# 4. Finalize CSV File
# ---------------------------

# Move temporary CSV to the desired output location
mv "$TEMP_CSV" "$OUTPUT_CSV"

echo "tsdetector-input.csv has been created at '$OUTPUT_CSV'."
echo "Total mappings generated: $(wc -l < "$OUTPUT_CSV")"

# Exit successfully
exit 0
