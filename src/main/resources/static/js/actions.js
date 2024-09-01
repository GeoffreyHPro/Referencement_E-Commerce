function goToPage(page) {
    // get the current url
    const url = new URL(window.location.href);
    let search_params = url.searchParams;

    // change the param page from the url
    search_params.set('page', page);
    url.search = search_params.toString();
    // the new url string
    const new_url = url.toString();

    // go to the new url
    window.location.href = new_url;
}
