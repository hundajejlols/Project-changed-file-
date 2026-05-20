import sys
import hashlib
import json
import os

HASH_DB_FILE = 'hashes.json'

def get_file_hash(filepath):
    hasher = hashlib.sha256()
    try:
        with open(filepath, 'rb') as f:
            hasher.update(f.read())
        return hasher.hexdigest()
    except FileNotFoundError:
        return None

def load_hashes():
    if os.path.exists(HASH_DB_FILE):
        with open(HASH_DB_FILE, 'r') as f:
            return json.load(f)
    return {}

def save_hashes(hashes):
    with open(HASH_DB_FILE, 'w') as f:
        json.dump(hashes, f, indent=4)

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Usage: python main.py <filename>")
        sys.exit(1)

    target_file = sys.argv[1]
    current_hash = get_file_hash(target_file)
    
    if current_hash is None:
        print(f"Error: File '{target_file}' not found.")
        sys.exit(1)

    hashes = load_hashes()

    if target_file in hashes:
        saved_hash = hashes[target_file]
        
        if current_hash == saved_hash:
            print(f"File '{target_file}' has not changed.")
        else:
            print(f"File '{target_file}' has changed.")
            hashes[target_file] = current_hash
            save_hashes(hashes)
    else:
        hashes[target_file] = current_hash
        save_hashes(hashes)
        print(f"New file '{target_file}' tracked successfully.")