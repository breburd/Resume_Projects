% Load the image
img = imread('chart.png');
gray_img = rgb2gray(img);  % Convert to grayscale if the image is in color

% Display the image
imshow(gray_img);
title('Original Image');

% Display the edges
edges = edge(gray_img, 'Canny');
figure;
imshow(edges);
title('Edge Detection');

% Apply a Gaussian blur to reduce grid visibility
blurred_img = imgaussfilt(gray_img, 2);  % Adjust the 2 to control the strength of the blur

% Re-run edge detection on the blurred image
edges = edge(blurred_img, 'Canny');
imshow(edges);
title('Edge Detection on Blurred Image');

% Apply morphological opening to remove thin grid lines
se = strel('line', 3, 0);  % Adjust the length of the line element as needed
opened_img = imopen(edges, se);

% Display the result
imshow(opened_img);
title('Morphologically Processed Image');

% % Use adaptive thresholding
% binary_img = imbinarize(blurred_img, 'adaptive', 'Sensitivity', 0.6);  % Adjust sensitivity as needed
% 
% % Edge detection on the binary image
% edges = edge(binary_img, 'Canny');
% imshow(edges);
% title('Edge Detection on Adaptive Thresholded Image');
% 
% % Perform FFT to transform the image into the frequency domain
% fft_img = fft2(double(gray_img));
% fft_shifted = fftshift(fft_img);  % Center the FFT
% 
% % Apply a high-pass filter to suppress regular grid frequencies
% [rows, cols] = size(fft_shifted);
% crow = round(rows/2);
% ccol = round(cols/2);
% radius = 15;  % Adjust radius to control filter size
% mask = ones(rows, cols);
% mask(crow-radius:crow+radius, ccol-radius:ccol+radius) = 0;
% 
% % Apply mask and inverse FFT
% filtered_fft = fft_shifted .* mask;
% filtered_img = real(ifft2(ifftshift(filtered_fft)));
% 
% % Edge detection on the filtered image
% edges = edge(filtered_img, 'Canny');
% imshow(edges);
% title('Edge Detection on Frequency Filtered Image');
% 
% % Remove small isolated pixels
% cleaned_edges = bwareaopen(edges, 50);  % Adjust the 50 threshold based on grid artifacts
% 
% % Display the cleaned edges
% imshow(cleaned_edges);
% title('Cleaned Edge Image without Isolated Pixels');

% Find coordinates of edge points
[y_coords, x_coords] = find(edges);

% Combine x and y into a matrix for easier handling
coordinates = [x_coords, y_coords];

% Define axis ranges
x_range = [0, 64];  % Common for all plots
y_ranges = {[-0.4, 0.4], [0, 1.25], [0.25, 0.55], [0, 1.25]};  % y ranges for each plot

% Get the pixel range for x and y based on image dimensions
x_pixel_range = [1, size(gray_img, 2)];
y_pixel_range = [1, size(gray_img, 1)];

% Scaling function to map pixel coordinates to data coordinates
scale_coordinates = @(coords, y_range) [
    x_range(1) + (coords(:,1) - x_pixel_range(1)) * (x_range(2) - x_range(1)) / (x_pixel_range(2) - x_pixel_range(1)), ...
    y_range(1) + (y_pixel_range(2) - coords(:,2)) * (y_range(2) - y_range(1)) / (y_pixel_range(2) - y_pixel_range(1))
];

% Initialize cell array to hold scaled data for each plot
scaled_data = cell(1, 4);

% Scale coordinates for each plot with corresponding y-range
for i = 1:4
    scaled_data{i} = scale_coordinates(coordinates, y_ranges{i});
end

for i = 1:4
    % Convert the matrix to a table for easier export
    data_table = array2table(scaled_data{i}, 'VariableNames', {'X', 'Y'});
    
    % Save to a CSV file
    writetable(data_table, sprintf('plot_%d.csv', i));
end