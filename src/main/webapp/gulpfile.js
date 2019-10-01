const gulp        = require('gulp');
const browserSync = require('browser-sync').create();
const sass        = require('gulp-sass');

// Compile Sass & Inject Into Browser
gulp.task('sass', function() {
    return gulp.src(['node_modules/bootstrap/scss/bootstrap.scss', 'clinicPlus_new/scss/*.scss'])
        .pipe(sass())
        .pipe(gulp.dest("clinicPlus_new/css"))
        .pipe(browserSync.stream());
});

// Move JS Files to clinicPlus_new/js
gulp.task('js', function() {
    return gulp.src(['node_modules/bootstrap/dist/js/bootstrap.min.js', 'node_modules/jquery/dist/jquery.min.js','node_modules/popper.js/dist/umd/popper.min.js'])
        .pipe(gulp.dest("clinicPlus_new/js"))
        .pipe(browserSync.stream());
});

// Watch Sass & Serve
gulp.task('serve', ['sass'], function() {

    browserSync.init({
        server: "./clinicPlus_new"  
    });

    gulp.watch(['node_modules/bootstrap/scss/bootstrap.scss', 'clinicPlus_new/scss/*.scss'], ['sass']);
    gulp.watch("clinicPlus_new/*.html").on('change', browserSync.reload);
});

// Move Fonts to clinicPlus_new/fonts
gulp.task('fonts', function() {
  return gulp.src('node_modules/font-awesome/fonts/*')
    .pipe(gulp.dest('clinicPlus_new/fonts'))
})

// Move Font Awesome CSS to clinicPlus_new/css
gulp.task('fa', function() {
  return gulp.src('node_modules/font-awesome/css/font-awesome.min.css')
    .pipe(gulp.dest('clinicPlus_new/css'))
})

gulp.task('default', ['js','serve', 'fa', 'fonts']);