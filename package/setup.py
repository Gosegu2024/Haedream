
import setuptools

with open("README.md", "r") as fh:
    long_description = fh.read()

setuptools.setup(
    name="haedream",
    version="0.0.8",
    author="GOSEGU",
    author_email="",
    description="This is a package for haedream",
    long_description=long_description,
    long_description_content_type="text/markdown",
    url="https://github.com/Gosegu2024/Haedream.git", ##
    packages=setuptools.find_packages(),
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
        "Operating System :: OS Independent",
    ],
    python_requires='>=3.6',
)