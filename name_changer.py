from os import walk
from os import rename
from os import path
from os import remove
from re import sub


def main(): 

    # folder path
    dir_path = r'/Users/oriengoulding/OneDrive/Test_folder'

    directories = []
    files = []
    if path.exists('log.txt'):
        remove('log.txt')

    for (dir_path, dir_names, file_names) in walk(dir_path):
        
        for dir_name in dir_names:
            sub = sub_invalid_char(dir_name)
            if sub != dir_name:
                directories.append(
                    {'path': dir_path
                    , 'name': dir_name
                    , 'sub_name': sub
                    , 'length': len(dir_path)
                    })

        for file_name in file_names:
            sub = sub_invalid_char(file_name)
            if sub != file_name:
                files.append(
                    {'path': dir_path
                    , 'name': file_name
                    , 'sub_name': sub
                    })

    directories = sorted(directories, key=lambda directory: directory['length'])
    write_list_txt(files, 'w')
    write_list_txt(directories, 'a')
    
    proceed = input('Please check the names.txt file and confirm whether ' \
          'the replacement characters are acceptable. \n' \
          'Do you wish to proceed? ')

    if proceed.lower() == 'y' or proceed.lower() == 'yes':
        replace_names(files)
        replace_names(directories)
        

def sub_invalid_char(name):
    return sub(r'\"|\*|\<|\>|\?|\/|\\|\|', '-', name)


def write_list_txt(list, write_type):
    with open('names.txt', write_type) as file:
        for item in reversed(list):
            file.write(f'{path.join(item["path"], item["name"])}\n')
            file.write(f'{path.join(item["path"], item["sub_name"])}\n\n')


def replace_names(list):
    for item in reversed(list):
        if path.exists(path.join(item['path'], item['sub_name'])):
            log_error(path.join(item['path'], item['name']),
                      path.join(item['path'], item['sub_name']),
                      'Replacement already exists')
        else:
            try:
                rename(path.join(item["path"], item["name"]),
                       path.join(item["path"], item["sub_name"]))
            except IsADirectoryError as e:
                log_error(path.join(item["path"], item["name"]),
                          path.join(item["path"], item["sub_name"]),
                          e)
            except NotADirectoryError as e:
                log_error(path.join(item["path"], item["name"]),
                          path.join(item["path"], item["sub_name"]),
                          e)
            except OSError as e:
                log_error(path.join(item["path"], item["name"]),
                          path.join(item["path"], item["sub_name"]),
                          e)
            
            
def log_error(src, dst, error):
    with open('log.txt', 'a') as file:
            file.write(f'{src} \n')
            file.write(f'{dst} \n')
            file.write(f'Error: {error} \n\n')


main()
