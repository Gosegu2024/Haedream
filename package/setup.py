
import setuptools

with open("README.md", "r", encoding="utf-8") as fh:
    long_description = fh.read()

setuptools.setup(
    name="haedream",
    version="0.0.9",
    author="GOSEGU",
    author_email="jhj990203@gmail.com",
    description="This is a package for haedream",
    long_description=long_description,
    long_description_content_type="text/markdown",
    url="https://github.com/Gosegu2024/Haedream.git",
    packages=setuptools.find_packages(),
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
        "Operating System :: OS Independent",
    ],
    python_requires='>=3.6',
)